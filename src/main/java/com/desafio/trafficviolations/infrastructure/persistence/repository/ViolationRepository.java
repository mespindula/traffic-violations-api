package com.desafio.trafficviolations.infrastructure.persistence.repository;

import com.desafio.trafficviolations.infrastructure.persistence.entity.ViolationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViolationRepository extends JpaRepository<ViolationEntity, Long> {
    Optional<ViolationEntity> findByEquipmentSerial(String serial);
}
