package com.desafio.trafficviolations.application.service;

import com.desafio.trafficviolations.application.exception.BusinessException;
import com.desafio.trafficviolations.application.exception.ResourceNotFoundException;
import com.desafio.trafficviolations.domain.model.Equipment;
import com.desafio.trafficviolations.domain.model.Violation;
import com.desafio.trafficviolations.domain.port.in.ViolationUseCase;
import com.desafio.trafficviolations.domain.port.out.EquipmentPersistencePort;
import com.desafio.trafficviolations.domain.port.out.ViolationPersistencePort;
import com.desafio.trafficviolations.domain.strategy.ViolationValidationFactory;
import com.desafio.trafficviolations.domain.strategy.ViolationValidationStrategy;
import com.desafio.trafficviolations.infrastructure.dto.ViolationRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.ViolationResponseDTO;
import com.desafio.trafficviolations.infrastructure.mapper.ViolationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViolationService implements ViolationUseCase {

    private static final int MAX_IMAGE_SIZE_BYTES = 1_048_576; // 1MB

    private final ViolationPersistencePort violationPersistencePort;
    private final EquipmentPersistencePort equipmentPersistencePort;
    private final ViolationValidationFactory validationFactory;

    public ViolationService(
            ViolationPersistencePort violationPersistencePort,
            EquipmentPersistencePort equipmentPersistencePort,
            ViolationValidationFactory validationFactory
    ) {
        this.violationPersistencePort = violationPersistencePort;
        this.equipmentPersistencePort = equipmentPersistencePort;
        this.validationFactory = validationFactory;
    }

    @Override
    @Transactional
    public ViolationResponseDTO register(ViolationRequestDTO dto) {
        // Verifica se equipamento existe
        Equipment equipment = equipmentPersistencePort.findBySerial(dto.getEquipmentSerial())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Equipamento com serial '" + dto.getEquipmentSerial() + "' não encontrado."));

        // Verifica se equipamento está ativo
        if (!equipment.isActive()) {
            throw new BusinessException("Não é possível registrar infrações em equipamento inativo.");
        }

        // Validações por tipo de infração
        ViolationValidationStrategy strategy = validationFactory.getStrategy(dto.getType());
        if (strategy != null) {
            strategy.validate(dto);
        }

        // 4. Valida a imagem
        if (dto.getPicture() != null && dto.getPicture().length > MAX_IMAGE_SIZE_BYTES) {
            throw new BusinessException("Imagem excede o tamanho máximo de 1MB.");
        }

        // 5. Define data da infração, se não informada
        dto.setOccurrenceDateUtc(dto.getOccurrenceDateUtc() != null ? dto.getOccurrenceDateUtc(): Instant.now());

        Violation violation = ViolationMapper.toDomain(dto, equipment);

        Violation saved = violationPersistencePort.save(violation);
        return ViolationMapper.toResponseDTO(saved);
    }

    @Override
    public ViolationResponseDTO getById(Long id) {
        return violationPersistencePort.getById(id)
                .map(ViolationMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Infração não encontrada: " + id));
    }

    @Override
    public List<ViolationResponseDTO> listByEquipment(String serial) {
        return violationPersistencePort.findByEquipmentSerial(serial)
                .stream()
                .map(ViolationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
