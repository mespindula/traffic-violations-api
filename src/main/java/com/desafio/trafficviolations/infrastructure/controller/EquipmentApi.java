package com.desafio.trafficviolations.infrastructure.controller;

import com.desafio.trafficviolations.infrastructure.dto.EquipmentRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Equipamentos", description = "Gerenciamento de equipamentos de fiscalização")
public interface EquipmentApi {

    @Operation(summary = "Cadastra um novo equipamento")
    ResponseEntity<EquipmentResponseDTO> create(@RequestBody EquipmentRequestDTO dto);

    @Operation(summary = "Lista todos os equipamentos")
    ResponseEntity<List<EquipmentResponseDTO>> findAll();

    @Operation(summary = "Busca um equipamento pelo serial")
    ResponseEntity<EquipmentResponseDTO> findBySerial(@PathVariable String serial);
}
