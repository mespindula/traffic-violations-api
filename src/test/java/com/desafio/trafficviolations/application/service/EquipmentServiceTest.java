package com.desafio.trafficviolations.application.service;

import com.desafio.trafficviolations.application.exception.BusinessException;
import com.desafio.trafficviolations.application.exception.ResourceNotFoundException;
import com.desafio.trafficviolations.domain.model.Equipment;
import com.desafio.trafficviolations.domain.port.out.EquipmentPersistencePort;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @InjectMocks
    private EquipmentService service;

    @Mock
    private EquipmentPersistencePort persistencePort;

    private EquipmentRequestDTO validRequest;

    @BeforeEach
    void setup() {
        validRequest = EquipmentRequestDTO.builder()
                .serial("EQ123")
                .model("RadarX")
                .address("Rua Teste")
                .latitude(BigDecimal.valueOf(-23.5))
                .longitude(BigDecimal.valueOf(-46.6))
                .active(true)
                .build();
    }

    @Test
    void create_shouldSaveAndReturnEquipment_whenSerialIsUnique() {
        when(persistencePort.findBySerial("EQ123")).thenReturn(Optional.empty());
        when(persistencePort.save(any(Equipment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EquipmentResponseDTO response = service.create(validRequest);

        assertEquals("EQ123", response.getSerial());
        assertEquals("RadarX", response.getModel());
        verify(persistencePort).save(any(Equipment.class));
    }

    @Test
    void create_shouldThrowBusinessException_whenSerialAlreadyExists() {
        Equipment existing = Equipment.builder().serial("EQ123").build();
        when(persistencePort.findBySerial("EQ123")).thenReturn(Optional.of(existing));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.create(validRequest));

        assertEquals("Equipamento com serial já existe: EQ123", ex.getMessage());
        verify(persistencePort, never()).save(any());
    }

    @Test
    void findBySerial_shouldReturnEquipment_whenFound() {
        Equipment equipment = Equipment.builder()
                .serial("ABC")
                .model("Radar Z")
                .address("Av. Brasil")
                .latitude(BigDecimal.valueOf(10.0))
                .longitude(BigDecimal.valueOf(20.0))
                .active(true)
                .build();

        when(persistencePort.findBySerial("ABC")).thenReturn(Optional.of(equipment));

        EquipmentResponseDTO result = service.findBySerial("ABC");

        assertEquals("ABC", result.getSerial());
        assertEquals("Radar Z", result.getModel());
    }

    @Test
    void findBySerial_shouldThrowNotFound_whenNotExists() {
        when(persistencePort.findBySerial("NOT_FOUND")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findBySerial("NOT_FOUND"));
    }

    @Test
    void findAll_shouldReturnListOfEquipments() {
        Equipment e1 = Equipment.builder().serial("1").build();
        Equipment e2 = Equipment.builder().serial("2").build();

        when(persistencePort.findAll()).thenReturn(List.of(e1, e2));

        List<EquipmentResponseDTO> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getSerial());
        assertEquals("2", result.get(1).getSerial());
    }
}
