package com.desafio.trafficviolations.infrastructure.dto;

import com.desafio.trafficviolations.domain.enums.ViolationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados de resposta da infração")
public class ViolationResponseDTO {

    @Schema(description = "Identificador único da infração", example = "123")
    private Long id;

    @Schema(description = "Data e hora da infração", example = "2025-07-13T10:15:30Z")
    private Instant date;

    @Schema(description = "Tipo da infração", example = "SPEEDING")
    private ViolationType type;

    @Schema(description = "Serial do equipamento que registrou a infração", example = "EQP-001")
    private String equipmentSerial;
}
