package com.desafio.trafficviolations.domain.strategy;

import com.desafio.trafficviolations.infrastructure.dto.ViolationRequestDTO;

public interface ViolationValidationStrategy {
    void validate(ViolationRequestDTO dto);
}
