package com.desafio.trafficviolations.infrastructure.controller;

import com.desafio.trafficviolations.domain.port.in.EquipmentUseCase;
import com.desafio.trafficviolations.infrastructure.dto.EquipmentResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(EquipmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentUseCase equipmentUseCase;

    private static final String BASE_URL = "/equipments";

    private EquipmentResponseDTO buildResponse() {
        return EquipmentResponseDTO.builder()
                .serial("ABC123")
                .model("Radar X1")
                .address("Av. Brasil, 123")
                .latitude(new BigDecimal("-23.5505"))
                .longitude(new BigDecimal("-46.6333"))
                .active(true)
                .build();
    }

    @Nested
    @DisplayName("POST /equipments")
    class Create {

        @Test
        @DisplayName("should return 200 and created equipment")
        void create_shouldReturnCreatedEquipment() throws Exception {
            EquipmentResponseDTO response = buildResponse();

            Mockito.when(equipmentUseCase.create(any())).thenReturn(response);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "serial": "ABC123",
                                      "model": "Radar X1",
                                      "address": "Av. Brasil, 123",
                                      "latitude": -23.5505,
                                      "longitude": -46.6333,
                                      "active": true
                                    }
                                    """))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.serial").value("ABC123"))
                    .andExpect(jsonPath("$.model").value("Radar X1"));
        }

        @Test
        @DisplayName("should return 400 when serial is blank")
        void create_shouldFailIfSerialIsBlank() throws Exception {
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "serial": "",
                                      "model": "Radar X1",
                                      "address": "Av. Brasil, 123",
                                      "latitude": -23.5505,
                                      "longitude": -46.6333,
                                      "active": true
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors.serial").value("O serial é obrigatório"));
        }

        @Test
        @DisplayName("should return 400 when model is missing")
        void create_shouldFailIfModelIsMissing() throws Exception {
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "serial": "ABC123",
                                      "model": "",
                                      "address": "Av. Brasil, 123",
                                      "latitude": -23.5505,
                                      "longitude": -46.6333,
                                      "active": true
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors.model").value("O modelo é obrigatório"));
        }

        @Test
        @DisplayName("should return 400 when latitude is invalid")
        void create_shouldFailIfLatitudeOutOfRange() throws Exception {
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "serial": "ABC123",
                                      "model": "Radar X1",
                                      "address": "Av. Brasil, 123",
                                      "latitude": -100.0,
                                      "longitude": -46.6333,
                                      "active": true
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors.latitude").value("Latitude deve ser ≥ -90"));

        }

        @Test
        @DisplayName("should return 400 when longitude is invalid")
        void create_shouldFailIfLongitudeOutOfRange() throws Exception {
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "serial": "ABC123",
                                      "model": "Radar X1",
                                      "address": "Av. Brasil, 123",
                                      "latitude": -23.5505,
                                      "longitude": -190.0,
                                      "active": true
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors.longitude").value("Longitude deve ser ≥ -180"));
        }
    }

    @Nested
    @DisplayName("GET /equipments")
    class FindAll {

        @Test
        @DisplayName("should return list of equipments")
        void findAll_shouldReturnEquipmentsList() throws Exception {
            EquipmentResponseDTO response = buildResponse();

            Mockito.when(equipmentUseCase.findAll()).thenReturn(List.of(response));

            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].serial", is("ABC123")));
        }
    }

    @Nested
    @DisplayName("GET /equipments/{serial}")
    class FindBySerial {

        @Test
        @DisplayName("should return equipment by serial")
        void findBySerial_shouldReturnEquipment() throws Exception {
            EquipmentResponseDTO response = buildResponse();

            Mockito.when(equipmentUseCase.findBySerial("ABC123")).thenReturn(response);

            mockMvc.perform(get(BASE_URL + "/ABC123"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.serial").value("ABC123"));
        }
    }
}
