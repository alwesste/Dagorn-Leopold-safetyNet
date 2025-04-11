package com.openclassrooms.safetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.services.impl.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class MedicalRecordControllerTest {

    @Autowired
    MedicalRecordsService medicalRecordsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private MedicalRecords medicalRecords;

    @BeforeEach
    void setUp() {
        medicalRecords = new MedicalRecords("John", "Boyd", "03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );
    }

    @Test
    void addMedicalRecordsControllerTest() throws Exception {

        mockMvc.perform(post("/medicalRecord")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medicalRecords)))
                .andExpect(status().isCreated());
    }

    @Test
    void medicalRecordsModifyTest() throws Exception {


        mockMvc.perform(put("/medicalRecord")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medicalRecords)))
                .andExpect(status().isAccepted());
    }


    @Test
    void medicalRecordsDeleteTest() throws Exception {

        mockMvc.perform(delete("/medicalRecord")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medicalRecords)))
                .andExpect(status().isNoContent());
    }

}
