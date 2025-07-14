package com.desafio.trafficviolations.infrastructure.persistence.mapper;

import com.desafio.trafficviolations.domain.model.Violation;
import com.desafio.trafficviolations.infrastructure.persistence.entity.ViolationEntity;

public class ViolationEntityMapper {

    public static ViolationEntity toEntity(Violation violation) {
        if (violation != null) {
            return ViolationEntity.builder()
                    .id(violation.getId())
                    .date(violation.getOccurrenceDateUtc())
                    .type(violation.getType())
                    .measuredSpeed(violation.getMeasuredSpeed())
                    .consideredSpeed(violation.getConsideredSpeed())
                    .regulatedSpeed(violation.getRegulatedSpeed())
                    .picture(violation.getPicture())
                    .equipment(EquipmentEntityMapper.toEntity(violation.getEquipment()))
                    .build();
        }
        return ViolationEntity.builder().build();
    }

    public static Violation toDomain(ViolationEntity entity) {
        if (entity != null) {
            return Violation.builder()
                    .id(entity.getId())
                    .occurrenceDateUtc(entity.getDate())
                    .type(entity.getType())
                    .measuredSpeed(entity.getMeasuredSpeed())
                    .consideredSpeed(entity.getConsideredSpeed())
                    .regulatedSpeed(entity.getRegulatedSpeed())
                    .picture(entity.getPicture())
                    .equipment(EquipmentEntityMapper.toDomain(entity.getEquipment()))
                    .build();
        }
        return Violation.builder().build();
    }
}
