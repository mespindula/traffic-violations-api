package com.desafio.trafficviolations.infrastructure.controller;

import com.desafio.trafficviolations.domain.enums.ViolationType;
import com.desafio.trafficviolations.domain.port.in.ViolationUseCase;
import com.desafio.trafficviolations.infrastructure.dto.ViolationRequestDTO;
import com.desafio.trafficviolations.infrastructure.dto.ViolationResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(ViolationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ViolationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViolationUseCase violationUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_shouldReturnCreatedViolation() throws Exception {
        ViolationRequestDTO request = ViolationRequestDTO.builder()
                .equipmentSerial("ABC123")
                .occurrenceDateUtc(Instant.parse("2025-07-13T10:00:00Z"))
                .type(ViolationType.VELOCITY)
                .measuredSpeed(BigDecimal.valueOf(80))
                .consideredSpeed(BigDecimal.valueOf(78))
                .regulatedSpeed(BigDecimal.valueOf(60))
                .picture(new byte[]{})
                .build();

        ViolationResponseDTO response = ViolationResponseDTO.builder()
                .id(1L)
                .type(ViolationType.VELOCITY)
                .equipmentSerial("ABC123")
                .date(Instant.parse("2025-07-13T10:00:00Z"))
                .build();

        when(violationUseCase.register(any())).thenReturn(response);

        mockMvc.perform(post("/violations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("VELOCITY"))
                .andExpect(jsonPath("$.equipmentSerial").value("ABC123"));
    }

    @Test
    void findById_shouldReturnViolation() throws Exception {
        ViolationResponseDTO response = ViolationResponseDTO.builder()
                .id(1L)
                .type(ViolationType.VELOCITY)
                .equipmentSerial("ABC123")
                .date(Instant.now())
                .build();

        when(violationUseCase.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/violations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("VELOCITY"));
    }

    @Test
    void findByEquipmentSerial_shouldReturnViolationsList() throws Exception {
        ViolationResponseDTO violation = ViolationResponseDTO.builder()
                .id(1L)
                .type(ViolationType.VELOCITY)
                .equipmentSerial("ABC123")
                .date(Instant.now())
                .build();

        when(violationUseCase.listByEquipment("ABC123")).thenReturn(List.of(violation));

        mockMvc.perform(get("/equipments/ABC123/violations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].equipmentSerial").value("ABC123"));
    }
}
