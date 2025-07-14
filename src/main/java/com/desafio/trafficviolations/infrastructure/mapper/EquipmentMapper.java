package com.desafio.trafficviolations.infrastructure.mapper;

import com.desafio.trafficviolations.domain.model.Equipment;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentResponseDTO;

public class EquipmentMapper {

    public static Equipment toDomain(EquipmentRequestDTO dto) {
        return Equipment.builder()
                .serial(dto.getSerial())
                .model(dto.getModel())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .active(dto.isActive())
                .build();
    }

    public static EquipmentResponseDTO toResponseDTO(Equipment equipment) {
        return EquipmentResponseDTO.builder()
                .id(equipment.getId())
                .serial(equipment.getSerial())
                .model(equipment.getModel())
                .address(equipment.getAddress())
                .latitude(equipment.getLatitude())
                .longitude(equipment.getLongitude())
                .active(equipment.isActive())
                .build();
    }
}
