package com.desafio.trafficviolations.infrastructure.persistence.adapter;

import com.desafio.trafficviolations.domain.model.Violation;
import com.desafio.trafficviolations.domain.port.out.ViolationPersistencePort;
import com.desafio.trafficviolations.infrastructure.persistence.entity.ViolationEntity;
import com.desafio.trafficviolations.infrastructure.persistence.mapper.ViolationEntityMapper;
import com.desafio.trafficviolations.infrastructure.persistence.repository.ViolationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ViolationJpaAdapter implements ViolationPersistencePort {

    private final ViolationRepository violationRepository;

    public ViolationJpaAdapter(ViolationRepository violationRepository) {
        this.violationRepository = violationRepository;
    }

    @Override
    public Violation save(Violation violation) {
        ViolationEntity entity = ViolationEntityMapper.toEntity(violation);
        ViolationEntity saved = violationRepository.save(entity);
        return ViolationEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Violation> getById(Long id) {
        return violationRepository.findById(id).map(ViolationEntityMapper::toDomain);
    }

    @Override
    public List<Violation> findByEquipmentSerial(String serial) {
        return violationRepository.findByEquipmentSerial(serial).stream()
                .map(ViolationEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
