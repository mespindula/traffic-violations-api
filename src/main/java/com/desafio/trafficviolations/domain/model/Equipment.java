package com.desafio.trafficviolations.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipment {

    private Long id;
    private String serial;
    private String model;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private boolean active;
}
