package com.desafio.trafficviolations.domain.port.out;

import com.desafio.trafficviolations.domain.model.Equipment;

import java.util.List;
import java.util.Optional;

public interface EquipmentPersistencePort {
    Equipment save(Equipment equipment);
    Optional<Equipment> findBySerial(String serial);
    List<Equipment> findAll();
}
