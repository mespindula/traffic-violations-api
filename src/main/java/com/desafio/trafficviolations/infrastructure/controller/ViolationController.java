package com.desafio.trafficviolations.infrastructure.controller;

import com.desafio.trafficviolations.infrastructure.dto.ViolationRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.ViolationResponseDTO;
import com.desafio.trafficviolations.domain.port.in.ViolationUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping
public class ViolationController implements ViolationApi {

    private final ViolationUseCase violationUseCase;

    public ViolationController(ViolationUseCase violationUseCase) {
        this.violationUseCase = violationUseCase;
    }

    @Override
    @PostMapping("/violations")
    public ResponseEntity<ViolationResponseDTO> create(@RequestBody @Valid ViolationRequestDTO dto) {
        return ResponseEntity.ok(violationUseCase.register(dto));
    }

    @Override
    @GetMapping("/violations/{id}")
    public ResponseEntity<ViolationResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(violationUseCase.getById(id));
    }

    @Override
    @GetMapping("/equipments/{serial}/violations")
    public ResponseEntity<List<ViolationResponseDTO>> findByEquipmentSerial(@PathVariable String serial) {
        return ResponseEntity.ok(violationUseCase.listByEquipment(serial));
    }
}
