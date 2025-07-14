package com.desafio.trafficviolations.infrastructure.mapper;

import com.desafio.trafficviolations.domain.model.Equipment;
import com.desafio.trafficviolations.domain.model.Violation;
import com.desafio.trafficviolations.infrastructure.dto.ViolationRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.ViolationResponseDTO;
import org.apache.commons.lang3.StringUtils;

public class ViolationMapper {

    public static Violation toDomain(ViolationRequestDTO dto, Equipment equipment) {
        return Violation.builder()
                .equipment(equipment)
                .occurrenceDateUtc(dto.getOccurrenceDateUtc())
                .type(dto.getType())
                .measuredSpeed(dto.getMeasuredSpeed())
                .consideredSpeed(dto.getConsideredSpeed())
                .regulatedSpeed(dto.getRegulatedSpeed())
                .picture(dto.getPicture())
                .build();
    }

    public static ViolationResponseDTO toResponseDTO(Violation violation) {
        return ViolationResponseDTO.builder()
                .id(violation.getId())
                .type(violation.getType())
                .date(violation.getOccurrenceDateUtc())
                .equipmentSerial(violation.getEquipment() != null ? violation.getEquipment().getSerial() : StringUtils.EMPTY)
                .build();
    }
}
