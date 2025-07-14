package com.desafio.trafficviolations.domain.model;

import com.desafio.trafficviolations.domain.enums.ViolationType;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Violation {

    private Long id;
    private Equipment equipment;
    private Instant occurrenceDateUtc;
    private ViolationType type;
    private BigDecimal measuredSpeed;
    private BigDecimal consideredSpeed;
    private BigDecimal regulatedSpeed;
    private byte[] picture;
}
