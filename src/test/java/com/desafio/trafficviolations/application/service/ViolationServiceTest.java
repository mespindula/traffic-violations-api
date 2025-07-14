package com.desafio.trafficviolations.application.service;

import com.desafio.trafficviolations.application.exception.BusinessException;
import com.desafio.trafficviolations.application.exception.ResourceNotFoundException;
import com.desafio.trafficviolations.domain.enums.ViolationType;
import com.desafio.trafficviolations.domain.model.Equipment;
import com.desafio.trafficviolations.domain.model.Violation;
import com.desafio.trafficviolations.domain.port.out.EquipmentPersistencePort;
import com.desafio.trafficviolations.domain.port.out.ViolationPersistencePort;
import com.desafio.trafficviolations.domain.strategy.ViolationValidationFactory;
import com.desafio.trafficviolations.domain.strategy.ViolationValidationStrategy;
import com.desafio.trafficviolations.infrastructure.dto.ViolationRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.ViolationResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViolationServiceTest {

    @InjectMocks
    private ViolationService service;

    @Mock
    private ViolationPersistencePort violationPersistencePort;

    @Mock
    private EquipmentPersistencePort equipmentPersistencePort;

    @Mock
    private ViolationValidationFactory validationFactory;

    @Mock
    private ViolationValidationStrategy validationStrategy;

    private static final byte[] VALID_PICTURE = new byte[1024]; // 1KB
    private static final byte[] LARGE_PICTURE = new byte[1_048_577]; // >1MB

    @Test
    void getById_shouldReturnViolation_whenFound() {
        Violation violation = buildViolation(1L);
        when(violationPersistencePort.getById(1L)).thenReturn(Optional.of(violation));

        ViolationResponseDTO result = service.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(ViolationType.VELOCITY, result.getType());
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        when(violationPersistencePort.getById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void findAll_shouldReturnList_whenViolationsExist() {
        List<Violation> violations = List.of(buildViolation(1L), buildViolation(2L));
        when(violationPersistencePort.findByEquipmentSerial("EQ123")).thenReturn(violations);

        List<ViolationResponseDTO> result = service.listByEquipment("EQ123");

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void register_shouldThrow_whenImageExceedsMaxSize() {
        ViolationRequestDTO dto = buildValidDTO();
        dto.setPicture(LARGE_PICTURE);

        when(equipmentPersistencePort.findBySerial("EQ123"))
                .thenReturn(Optional.of(buildActiveEquipment()));

        assertThrows(BusinessException.class, () -> service.register(dto));
    }

    @Test
    void register_shouldUseNow_whenOccurrenceDateIsNull() {
        ViolationRequestDTO dto = buildValidDTO();
        dto.setOccurrenceDateUtc(null);

        Equipment equipment = buildActiveEquipment();

        when(equipmentPersistencePort.findBySerial("EQ123")).thenReturn(Optional.of(equipment));
        when(validationFactory.getStrategy(ViolationType.VELOCITY)).thenReturn(validationStrategy);
        doNothing().when(validationStrategy).validate(dto);

        Instant before = Instant.now();

        when(violationPersistencePort.save(any())).thenAnswer(inv -> {
            Violation violation = inv.getArgument(0);
            violation.setId(1L);
            assertNotNull(violation.getOccurrenceDateUtc(), "Date should have been set");

            assertTrue(!violation.getOccurrenceDateUtc().isBefore(before), "Date should be >= before");
            assertTrue(!violation.getOccurrenceDateUtc().isAfter(Instant.now()), "Date should be <= now");

            return violation;
        });

        // when
        ViolationResponseDTO response = service.register(dto);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }


    @Test
    void register_shouldSucceedWithoutStrategy() {
        ViolationRequestDTO dto = buildValidDTO();

        when(equipmentPersistencePort.findBySerial("EQ123")).thenReturn(Optional.of(buildActiveEquipment()));
        when(validationFactory.getStrategy(ViolationType.VELOCITY)).thenReturn(null);
        when(violationPersistencePort.save(any())).thenAnswer(inv -> {
            Violation violation = inv.getArgument(0);
            violation.setId(1L);
            return violation;
        });

        ViolationResponseDTO response = service.register(dto);

        assertNotNull(response);
        assertEquals(ViolationType.VELOCITY, response.getType());
        verify(validationStrategy, never()).validate(any());
    }

    @Test
    void register_shouldThrow_whenEquipmentNotFound() {
        ViolationRequestDTO dto = buildValidDTO();

        when(equipmentPersistencePort.findBySerial("EQ123"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.register(dto));
    }

    @Test
    void register_shouldThrow_whenEquipmentIsInactive() {
        ViolationRequestDTO dto = buildValidDTO();

        Equipment inactiveEquipment = Equipment.builder()
                .serial("EQ123")
                .active(false)
                .build();

        when(equipmentPersistencePort.findBySerial("EQ123"))
                .thenReturn(Optional.of(inactiveEquipment));

        assertThrows(BusinessException.class, () -> service.register(dto));
    }

    @Test
    void register_shouldThrow_whenMeasuredSpeedIsInvalid() {
        ViolationRequestDTO dto = buildValidDTO();
        dto.setMeasuredSpeed(BigDecimal.ZERO);

        when(equipmentPersistencePort.findBySerial("EQ123")).thenReturn(Optional.of(buildActiveEquipment()));
        when(validationFactory.getStrategy(ViolationType.VELOCITY)).thenReturn(validationStrategy);

        doThrow(new BusinessException("Velocidade medida deve ser maior que zero."))
                .when(validationStrategy).validate(dto);

        BusinessException exception = assertThrows(BusinessException.class, () -> service.register(dto));
        assertEquals("Velocidade medida deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void register_shouldThrow_whenRegulatedSpeedIsInvalid() {
        ViolationRequestDTO dto = buildValidDTO();
        dto.setRegulatedSpeed(BigDecimal.ZERO);

        when(equipmentPersistencePort.findBySerial("EQ123")).thenReturn(Optional.of(buildActiveEquipment()));
        when(validationFactory.getStrategy(ViolationType.VELOCITY)).thenReturn(validationStrategy);

        doThrow(new BusinessException("Velocidade regulamentada deve ser maior que zero."))
                .when(validationStrategy).validate(dto);

        BusinessException exception = assertThrows(BusinessException.class, () -> service.register(dto));
        assertEquals("Velocidade regulamentada deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void register_shouldThrow_whenConsideredSpeedIsInvalid() {
        ViolationRequestDTO dto = buildValidDTO();
        dto.setRegulatedSpeed(BigDecimal.ZERO);

        when(equipmentPersistencePort.findBySerial("EQ123")).thenReturn(Optional.of(buildActiveEquipment()));
        when(validationFactory.getStrategy(ViolationType.VELOCITY)).thenReturn(validationStrategy);

        doThrow(new BusinessException("Velocidade considerada deve ser maior que zero."))
                .when(validationStrategy).validate(dto);

        BusinessException exception = assertThrows(BusinessException.class, () -> service.register(dto));
        assertEquals("Velocidade considerada deve ser maior que zero.", exception.getMessage());
    }


    private Violation buildViolation(Long id) {
        return Violation.builder()
                .id(id)
                .type(ViolationType.VELOCITY)
                .occurrenceDateUtc(Instant.now())
                .equipment(buildActiveEquipment())
                .build();
    }

    private ViolationRequestDTO buildValidDTO() {
        return ViolationRequestDTO.builder()
                .equipmentSerial("EQ123")
                .type(ViolationType.VELOCITY)
                .occurrenceDateUtc(Instant.parse("2025-07-13T10:00:00Z"))
                .measuredSpeed(BigDecimal.valueOf(80))
                .consideredSpeed(BigDecimal.valueOf(78))
                .regulatedSpeed(BigDecimal.valueOf(60))
                .picture(VALID_PICTURE)
                .build();
    }

    private Equipment buildActiveEquipment() {
        return Equipment.builder()
                .serial("EQ123")
                .active(true)
                .build();
    }

}
