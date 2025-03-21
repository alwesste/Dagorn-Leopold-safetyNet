package com.openclassrooms.safetyNet;


import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.services.JsonFileHandler;
import com.openclassrooms.safetyNet.services.PersonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonsServiceTest {

    @InjectMocks
    private PersonsService personsService;

    @Mock
    private JsonFileHandler jsonFileHandler;

    DataJsonHandler mockDataJsonHandler;

    @BeforeEach
    public void setUp() throws IOException {
        mockDataJsonHandler = new DataJsonHandler();

        Firestations firestations1 = new Firestations("1509 Culver St", "1");
        List<Firestations> firestationsList = new ArrayList<>(List.of(firestations1));

        Persons personJohn = new Persons("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");
        List <Persons> personsList = new ArrayList<>(List.of(personJohn));

        MedicalRecords medicalRecordsOfJohn = new MedicalRecords("John", "Boyd", "03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );

        mockDataJsonHandler.setFirestations(firestationsList);
        mockDataJsonHandler.setPersons(personsList);
        mockDataJsonHandler.setMedicalrecords(List.of(medicalRecordsOfJohn));

        when(jsonFileHandler.readJsonFile()).thenReturn(mockDataJsonHandler);
    }

    @Test
    public void addPersonTest() throws IOException {
        Persons newPersonToAdd = new Persons("Clara", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "Clara@email.com");
        personsService.addPerson(newPersonToAdd);

        List<Persons> personsList = jsonFileHandler.readJsonFile().getPersons();

        assertNotNull(personsList, "La liste des persons n'est pas null");
        assertTrue(personsList.contains(newPersonToAdd), "La personne a bien été ajoutée correctement.");
    }

    @Test
    public void modifyFireStation() throws IOException, PersonNotFoundException {
        Persons updatePerson = new Persons("John", "Boyd", "new address", "new city", "11111", "111 111 1111", "Clara@email.com");

        personsService.modifyPerson(updatePerson);

        Persons modifiedPerson = mockDataJsonHandler.getPersons().stream()
                .filter(p -> p.getFirstName().equals("John") && p.getLastName().equals("Boyd"))
                .findFirst()
                .orElse(null);

        assertNotNull(modifiedPerson, "La personne modifiée devrait être présente dans la liste.");
        assertEquals("new address", modifiedPerson.getAddress(), "L'adresse de Clara est incorrecte.");
        assertEquals("new city", modifiedPerson.getCity(), "La ville de Clara est incorrecte.");
        assertEquals("11111", modifiedPerson.getZip(), "Le code postal de Clara est incorrect.");
        assertEquals("111 111 1111", modifiedPerson.getPhone(), "Le téléphone de Clara est incorrect.");
    }

    @Test
    public void personToDelete() throws PersonNotFoundException, IOException {
        Persons personToDelete = new Persons("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");
        personsService.deletePerson(personToDelete);

        List<Persons> allPersons = jsonFileHandler.readJsonFile().getPersons();

        assertFalse(allPersons.contains(personToDelete));
    }
}
