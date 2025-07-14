package com.desafio.trafficviolations.domain.strategy;

import com.desafio.trafficviolations.domain.enums.ViolationType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class ViolationValidationFactory {

    private final Map<ViolationType, ViolationValidationStrategy> strategyMap = new EnumMap<>(ViolationType.class);

    public ViolationValidationFactory(List<ViolationValidationStrategy> strategies) {
        for (ViolationValidationStrategy strategy : strategies) {
            if (strategy instanceof VelocityViolationStrategy) {
                strategyMap.put(ViolationType.VELOCITY, strategy);
            }
        }
    }

    public ViolationValidationStrategy getStrategy(ViolationType type) {
        return strategyMap.get(type);
    }
}
