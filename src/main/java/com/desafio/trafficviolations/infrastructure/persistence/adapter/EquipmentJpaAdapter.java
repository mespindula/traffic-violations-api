package com.desafio.trafficviolations.infrastructure.persistence.adapter;

import com.desafio.trafficviolations.domain.model.Equipment;
import com.desafio.trafficviolations.domain.port.out.EquipmentPersistencePort;
import com.desafio.trafficviolations.infrastructure.persistence.entity.EquipmentEntity;
import com.desafio.trafficviolations.infrastructure.persistence.mapper.EquipmentEntityMapper;
import com.desafio.trafficviolations.infrastructure.persistence.repository.EquipmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EquipmentJpaAdapter implements EquipmentPersistencePort {

    private final EquipmentRepository equipmentRepository;

    public EquipmentJpaAdapter(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Optional<Equipment> findBySerial(String serial) {
        return equipmentRepository.findBySerial(serial)
                .map(EquipmentEntityMapper::toDomain);
    }

    @Override
    public List<Equipment> findAll() {
        return equipmentRepository.findAll()
                .stream()
                .map(EquipmentEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Equipment save(Equipment equipment) {
        EquipmentEntity entity = EquipmentEntityMapper.toEntity(equipment);
        EquipmentEntity saved = equipmentRepository.save(entity);
        return EquipmentEntityMapper.toDomain(saved);
    }
}
