package com.desafio.trafficviolations.infrastructure.persistence.mapper;

import com.desafio.trafficviolations.domain.model.Equipment;
import com.desafio.trafficviolations.infrastructure.persistence.entity.EquipmentEntity;

public class EquipmentEntityMapper {

    public static EquipmentEntity toEntity(Equipment equipment) {
        if (equipment != null) {
            return EquipmentEntity.builder()
                    .serial(equipment.getSerial())
                    .model(equipment.getModel())
                    .address(equipment.getAddress())
                    .latitude(equipment.getLatitude())
                    .longitude(equipment.getLongitude())
                    .active(equipment.isActive())
                    .build();
        }
        return EquipmentEntity.builder().build();
    }

    public static Equipment toDomain(EquipmentEntity entity) {
        if (entity != null) {
            return Equipment.builder()
                    .serial(entity.getSerial())
                    .model(entity.getModel())
                    .address(entity.getAddress())
                    .latitude(entity.getLatitude())
                    .longitude(entity.getLongitude())
                    .active(entity.isActive())
                    .build();
        }
        return Equipment.builder().build();
    }
}
