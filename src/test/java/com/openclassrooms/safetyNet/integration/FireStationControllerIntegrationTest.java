package com.openclassrooms.safetyNet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.result.StationCover;
import com.openclassrooms.safetyNet.services.FirestationsService;
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
public class FireStationControllerIntegrationTest {

    @Autowired
    private FirestationsService firestationsService;

    //permet de tester les controlleur en simulant des requetes HTTP.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Firestations station;

    private StationCover stationCover;

    @BeforeEach
    void setUp() throws IOException {
        station = new Firestations("1509 Culver St", "5");
        firestationsService.getCoverPersons(1);
    }

    @Test
    void GetPersonsCoverByFireStationTest() throws Exception {

        mockMvc.perform(get("/firestation/stationNumber")
                        .param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultsCount").value(5))
                .andExpect(jsonPath("$.childrenCount").value(1))
                .andExpect(jsonPath("$.persons[0].firstName").value("Peter"))
                .andExpect(jsonPath("$.persons[0].lastName").value("Duncan"))
                .andExpect(jsonPath("$.persons[1].firstName").value("Reginold"))
                .andExpect(jsonPath("$.persons[1].lastName").value("Walker"));
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

        Firestations stationModified = new Firestations("1509 Culver St", "15");

        mockMvc.perform(put("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(stationModified)))
                .andExpect(status().isAccepted());

    }

    @Test
    void deleteStationTest() throws Exception {

        Firestations stationToDelete = new Firestations("1509 Culver St", "15");

        mockMvc.perform(delete("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(stationToDelete)))
                .andExpect(status().isNoContent());
    }
}