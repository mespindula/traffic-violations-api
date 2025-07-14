package com.desafio.trafficviolations.infrastructure.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentRequestDTO {

    @NotBlank(message = "O serial é obrigatório")
    private String serial;

    @NotBlank(message = "O modelo é obrigatório")
    private String model;

    @NotBlank(message = "O endereço é obrigatório")
    private String address;

    @DecimalMin(value = "-90.0", message = "Latitude deve ser ≥ -90")
    @DecimalMax(value = "90.0", message = "Latitude deve ser ≤ 90")
    private BigDecimal latitude;

    @DecimalMin(value = "-180.0", message = "Longitude deve ser ≥ -180")
    @DecimalMax(value = "180.0", message = "Longitude deve ser ≤ 180")
    private BigDecimal longitude;

    private boolean active;
}
