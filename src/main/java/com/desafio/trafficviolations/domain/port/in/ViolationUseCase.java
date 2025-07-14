package com.desafio.trafficviolations.domain.port.in;


import com.desafio.trafficviolations.infrastructure.dto.ViolationRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.ViolationResponseDTO;

import java.util.List;

public interface ViolationUseCase {
    ViolationResponseDTO register(ViolationRequestDTO dto);
    ViolationResponseDTO getById(Long id);
    List<ViolationResponseDTO> listByEquipment(String serial);
}
