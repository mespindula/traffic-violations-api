package com.desafio.trafficviolations.domain.port.in;

import com.desafio.trafficviolations.infrastructure.dto.EquipmentRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentResponseDTO;

import java.util.List;

public interface EquipmentUseCase {
    EquipmentResponseDTO create(EquipmentRequestDTO dto);
    EquipmentResponseDTO findBySerial(String serial);
    List<EquipmentResponseDTO> findAll();
}

