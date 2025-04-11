package com.openclassrooms.safetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.services.impl.FirestationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc //@AutoConfigureMockMvc configure un environnement de test pour faire des appels HTTP.
@ActiveProfiles(profiles = "test") //c'est pour utilise le profil test application-(test)
public class FireStationControllerTest {

    @Autowired
    private FirestationsService firestationsService;

    //permet de tester les controlleur en simulant des requetes HTTP.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Firestation station;

    @BeforeEach
    void setUp() throws IOException {
        station = new Firestation("1509 Culver St", "5");
        firestationsService.getCoverPersons(1);
    }

    @Test
    void GetPersonsCoverByFireStationTest() throws Exception {

        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].adultsCount").value(5))
                .andExpect(jsonPath("$[0].childrenCount").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Peter"))
                .andExpect(jsonPath("$[0]lastName").value("Duncan"))
                .andExpect(jsonPath("$[1]firstName").value("Reginold"))
                .andExpect(jsonPath("$[1]lastName").value("Walker"));
    }


    @Test
    void addFireStationTest() throws Exception {

        mockMvc.perform(post("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(station)))
                .andExpect(status().isCreated());

    }

    @Test
    void modifyStationTest() throws Exception {

        Firestation stationModified = new Firestation("1509 Culver St", "15");

        mockMvc.perform(put("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(stationModified)))
                .andExpect(status().isAccepted());

    }

    @Test
    void deleteStationTest() throws Exception {

        Firestation stationToDelete = new Firestation("1509 Culver St", "15");

        mockMvc.perform(delete("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(stationToDelete)))
                .andExpect(status().isNoContent());
    }
}