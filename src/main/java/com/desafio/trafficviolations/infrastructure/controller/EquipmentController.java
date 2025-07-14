package com.desafio.trafficviolations.infrastructure.controller;

import com.desafio.trafficviolations.infrastructure.dto.EquipmentRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentResponseDTO;
import com.desafio.trafficviolations.domain.port.in.EquipmentUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/equipments")
public class EquipmentController implements EquipmentApi {

    private final EquipmentUseCase equipmentUseCase;

    public EquipmentController(EquipmentUseCase equipmentUseCase) {
        this.equipmentUseCase = equipmentUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<EquipmentResponseDTO> create(@RequestBody @Valid EquipmentRequestDTO dto) {
        return ResponseEntity.ok(equipmentUseCase.create(dto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EquipmentResponseDTO>> findAll() {
        return ResponseEntity.ok(equipmentUseCase.findAll());
    }

    @Override
    @GetMapping("/{serial}")
    public ResponseEntity<EquipmentResponseDTO> findBySerial(@PathVariable String serial) {
        return ResponseEntity.ok(equipmentUseCase.findBySerial(serial));
    }
}
