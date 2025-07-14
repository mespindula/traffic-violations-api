package com.desafio.trafficviolations.application.service;

import com.desafio.trafficviolations.application.exception.BusinessException;
import com.desafio.trafficviolations.application.exception.ResourceNotFoundException;
import com.desafio.trafficviolations.domain.model.Equipment;
import com.desafio.trafficviolations.domain.port.in.EquipmentUseCase;
import com.desafio.trafficviolations.domain.port.out.EquipmentPersistencePort;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentResponseDTO;
import com.desafio.trafficviolations.infrastructure.mapper.EquipmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentService implements EquipmentUseCase {

    private final EquipmentPersistencePort equipmentPersistencePort;

    public EquipmentService(EquipmentPersistencePort equipmentPersistencePort) {
        this.equipmentPersistencePort = equipmentPersistencePort;
    }

    @Override
    @Transactional
    public EquipmentResponseDTO create(EquipmentRequestDTO dto) {
        if (equipmentPersistencePort.findBySerial(dto.getSerial()).isPresent()) {
            throw new BusinessException("Equipamento com serial já existe: " + dto.getSerial());
        }

        Equipment equipment = EquipmentMapper.toDomain(dto);
        Equipment saved = equipmentPersistencePort.save(equipment);

        return EquipmentMapper.toResponseDTO(saved);
    }

    @Override
    public EquipmentResponseDTO findBySerial(String serial) {
        Equipment equipment = equipmentPersistencePort.findBySerial(serial)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado. Serial: " + serial));
        return EquipmentMapper.toResponseDTO(equipment);
    }

    @Override
    public List<EquipmentResponseDTO> findAll() {
        return equipmentPersistencePort.findAll()
                .stream()
                .map(EquipmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
