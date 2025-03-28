package com.openclassrooms.safetyNet.integration;

import com.openclassrooms.safetyNet.controllers.DataControllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class DataControllerIntegrationTest {

    @Autowired
    DataControllers dataControllers;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getChild() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Tenley"))
                .andExpect(jsonPath("$[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$[0].age").value(13))
                .andExpect(jsonPath("$[0].familyMembers.size()").value(3))  // Vérifie que l'enfant a 2 membres de famille
                .andExpect(jsonPath("$[0].familyMembers[0].firstName").value("Jacob"))
                .andExpect(jsonPath("$[0].familyMembers[0].lastName").value("Boyd"))

                .andExpect(jsonPath("$[1].firstName").value("Roger"))
                .andExpect(jsonPath("$[1].lastName").value("Boyd"))
                .andExpect(jsonPath("$[1].age").value(7))
                .andExpect(jsonPath("$[1].familyMembers.size()").value(3))  // Vérifie que l'enfant a 2 membres de famille
                .andExpect(jsonPath("$[1].familyMembers[0].firstName").value("Jacob"))
                .andExpect(jsonPath("$[1].familyMembers[0].lastName").value("Boyd"));
    }

    @Test
    public void getPhoneNumberFromStation() throws Exception {
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(6))
                .andExpect(jsonPath("$[0].phone").value("841-874-6512"))
                .andExpect(jsonPath("$[1].phone").value("841-874-8547"))
                .andExpect(jsonPath("$[2].phone").value("841-874-7462"))
                .andExpect(jsonPath("$[3].phone").value("841-874-7784"))
                .andExpect(jsonPath("$[4].phone").value("841-874-7784"))
                .andExpect(jsonPath("$[5].phone").value("841-874-7784"));
    }

    @Test
    public void getFireHabitantByAddress() throws Exception {
        mockMvc.perform(get("/fire")
                        .param("address", "1509 Culver St"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))

                .andExpect(jsonPath("$[0].firstname").value("Jacob"))
                .andExpect(jsonPath("$[0].lastname").value("Boyd"))
                .andExpect(jsonPath("$[0].phone").value("841-874-6513"))
                .andExpect(jsonPath("$[0].age").value(36))
                .andExpect(jsonPath("$[0].medicalHistories[0].medicine.size()").value(3))
                .andExpect(jsonPath("$[0].medicalHistories[0].medicine[0]").value("pharmacol:5000mg"))
                .andExpect(jsonPath("$[0].medicalHistories[0].medicine[1]").value("terazine:10mg"))
                .andExpect(jsonPath("$[0].medicalHistories[0].medicine[2]").value("noznazol:250mg"))
                .andExpect(jsonPath("$[0].medicalHistories[0].allergie.size()").value(0))
                .andExpect(jsonPath("$[0].stationNumber").value("5"))

                .andExpect(jsonPath("$[1].firstname").value("Tenley"))
                .andExpect(jsonPath("$[1].lastname").value("Boyd"))
                .andExpect(jsonPath("$[1].phone").value("841-874-6512"))
                .andExpect(jsonPath("$[1].age").value(13))
                .andExpect(jsonPath("$[1].medicalHistories[0].medicine.size()").value(0))
                .andExpect(jsonPath("$[1].medicalHistories[0].allergie.size()").value(1))
                .andExpect(jsonPath("$[1].medicalHistories[0].allergie[0]").value("peanut"))
                .andExpect(jsonPath("$[1].stationNumber").value("5"));
    }

    @Test
    public void getFloodHabitant() throws Exception {
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(11))

                .andExpect(jsonPath("$[0].firstname").value("Jonanathan"))
                .andExpect(jsonPath("$[0].lastname").value("Marrack"))
                .andExpect(jsonPath("$[0].phone").value("841-874-6513"))
                .andExpect(jsonPath("$[0].age").value(36))
                .andExpect(jsonPath("$[0].medicalHistories[0].medicine.size()").value(0))
                .andExpect(jsonPath("$[0].medicalHistories[0].allergie.size()").value(0))

                .andExpect(jsonPath("$[1].firstname").value("Peter"))
                .andExpect(jsonPath("$[1].lastname").value("Duncan"))
                .andExpect(jsonPath("$[1].phone").value("841-874-6512"))
                .andExpect(jsonPath("$[1].age").value(24))
                .andExpect(jsonPath("$[1].medicalHistories[0].medicine.size()").value(0))
                .andExpect(jsonPath("$[1].medicalHistories[0].allergie.size()").value(1))
                .andExpect(jsonPath("$[1].medicalHistories[0].allergie[0]").value("shellfish"));
    }


    @Test
    public void getPersonInfoFromLastName() throws Exception {
        mockMvc.perform(get("/personInfolastName")
                        .param("lastName", "Boyd"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].firstName").value("Jacob"))
                .andExpect(jsonPath("$[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$[0].address").value("1509 Culver St"))
                .andExpect(jsonPath("$[0].age").value(36))
                .andExpect(jsonPath("$[0].email").value("drk@email.com"))
                .andExpect(jsonPath("$[0].medicalHistories[0].medicine.size()").value(3))
                .andExpect(jsonPath("$[0].medicalHistories[0].medicine[0]").value("pharmacol:5000mg"))
                .andExpect(jsonPath("$[0].medicalHistories[0].medicine[1]").value("terazine:10mg"))
                .andExpect(jsonPath("$[0].medicalHistories[0].medicine[2]").value("noznazol:250mg"))
                .andExpect(jsonPath("$[0].medicalHistories[0].allergie.size()").value(0))

                .andExpect(jsonPath("$[1].firstName").value("Tenley"))
                .andExpect(jsonPath("$[1].lastName").value("Boyd"))
                .andExpect(jsonPath("$[1].address").value("1509 Culver St"))
                .andExpect(jsonPath("$[1].age").value(13))
                .andExpect(jsonPath("$[1].email").value("tenz@email.com"))
                .andExpect(jsonPath("$[1].medicalHistories[0].medicine.size()").value(0))
                .andExpect(jsonPath("$[1].medicalHistories[0].allergie.size()").value(1))
                .andExpect(jsonPath("$[1].medicalHistories[0].allergie[0]").value("peanut"));
    }

    @Test
    public void getAllEmailFromCity() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .param("city", "Culver"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(22))
                .andExpect(jsonPath("$[0]").value("drk@email.com"))
                .andExpect(jsonPath("$[1]").value("tenz@email.com"))
                .andExpect(jsonPath("$[2]").value("jaboyd@email.com"))
                .andExpect(jsonPath("$[3]").value("jaboyd@email.com"))
                .andExpect(jsonPath("$[4]").value("drk@email.com"))
                .andExpect(jsonPath("$[5]").value("tenz@email.com"));
    }
}
