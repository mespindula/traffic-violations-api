package com.desafio.trafficviolations.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados de resposta do equipamento")
public class EquipmentResponseDTO {

    @Schema(description = "Identificador único do equipamento", example = "1")
    private Long id;

    @Schema(description = "Número de série do equipamento", example = "EQP-001")
    private String serial;

    @Schema(description = "Modelo do equipamento", example = "Radar XYZ")
    private String model;

    @Schema(description = "Endereço do local do equipamento", example = "Av. Brasil, 1000")
    private String address;

    @Schema(description = "Latitude do equipamento", example = "-23.550520")
    private BigDecimal latitude;

    @Schema(description = "Longitude do equipamento", example = "-46.633308")
    private BigDecimal longitude;

    @Schema(description = "Indica se o equipamento está ativo", example = "true")
    private boolean active;
}
