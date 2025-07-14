package com.desafio.trafficviolations.infrastructure.dto;

import com.desafio.trafficviolations.domain.enums.ViolationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViolationRequestDTO {

    @NotBlank(message = "O serial do equipamento é obrigatório")
    private String equipmentSerial;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant occurrenceDateUtc;

    @NotNull(message = "O tipo da infração é obrigatório")
    private ViolationType type;

    private BigDecimal measuredSpeed;

    private BigDecimal consideredSpeed;

    private BigDecimal regulatedSpeed;

    @Size(max = 1048576, message = "A imagem não pode exceder 1MB")
    private byte[] picture;
}
