package com.desafio.trafficviolations.infrastructure.controller;

import com.desafio.trafficviolations.infrastructure.dto.ViolationRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.ViolationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Infrações", description = "Gerenciamento de infrações registradas por equipamentos")
public interface ViolationApi {

    @Operation(summary = "Registra uma nova infração")
    ResponseEntity<ViolationResponseDTO> create(@RequestBody ViolationRequestDTO dto);

    @Operation(summary = "Busca uma infração por ID")
    ResponseEntity<ViolationResponseDTO> findById(@PathVariable Long id);

    @Operation(summary = "Lista infrações de um equipamento")
    ResponseEntity<List<ViolationResponseDTO>> findByEquipmentSerial(@PathVariable String serial);
}
