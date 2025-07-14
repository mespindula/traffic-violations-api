package com.desafio.trafficviolations.infrastructure.persistence.repository;

import com.desafio.trafficviolations.infrastructure.persistence.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Long> {
    Optional<EquipmentEntity> findBySerial(String serial);
}
