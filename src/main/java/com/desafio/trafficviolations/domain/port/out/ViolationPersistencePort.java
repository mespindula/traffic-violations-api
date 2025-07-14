package com.desafio.trafficviolations.domain.port.out;

import com.desafio.trafficviolations.domain.model.Violation;

import java.util.List;
import java.util.Optional;

public interface ViolationPersistencePort {
    Violation save(Violation violation);
    Optional<Violation> getById(Long id);
    List<Violation> findByEquipmentSerial(String serial);
}
