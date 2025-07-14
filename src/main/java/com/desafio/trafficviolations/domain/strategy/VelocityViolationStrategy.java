package com.desafio.trafficviolations.domain.strategy;

import com.desafio.trafficviolations.application.exception.BusinessException;
import com.desafio.trafficviolations.infrastructure.dto.ViolationRequestDTO;
import com.desafio.trafficviolations.domain.enums.ViolationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VelocityViolationStrategy implements ViolationValidationStrategy {

    @Override
    public void validate(ViolationRequestDTO dto) {
        if (dto.getType() != ViolationType.VELOCITY) return;

        if (dto.getMeasuredSpeed() == null || dto.getMeasuredSpeed().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Velocidade medida deve ser maior que zero.");
        }
        if (dto.getConsideredSpeed() == null || dto.getConsideredSpeed().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Velocidade considerada deve ser maior que zero.");
        }
        if (dto.getRegulatedSpeed() == null || dto.getRegulatedSpeed().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Velocidade regulamentada deve ser maior que zero.");
        }
    }
}
