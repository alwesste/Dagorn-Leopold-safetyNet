package com.openclassrooms.safetyNet.Repository;

import com.openclassrooms.safetyNet.SafetyNetApplication;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.MedicalRecord;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.repository.PersonRepository;
import com.openclassrooms.safetyNet.repository.JsonFileHandler;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SafetyNetApplication.class)
@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {

    @InjectMocks
    private PersonRepository personRepository;

    @Mock
    private JsonFileHandler jsonFileHandler;

    DataJsonHandler mockDataJsonHandler;


    @BeforeEach
    public void setUp() throws IOException, PersonNotFoundException {
        mockDataJsonHandler = new DataJsonHandler();

        Firestation firestation1 = new Firestation("1509 Culver St", "1");
        List<Firestation> firestationList = new ArrayList<>(List.of(firestation1));

        Person personJohn = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");
        List <Person> personsList = new ArrayList<>(List.of(personJohn));

        MedicalRecord medicalRecordsOfJohn = new MedicalRecord("John", "Boyd", "03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );

        mockDataJsonHandler.setFirestations(firestationList);
        mockDataJsonHandler.setPersons(personsList);
        mockDataJsonHandler.setMedicalrecords(List.of(medicalRecordsOfJohn));

        when(jsonFileHandler.readJsonFile()).thenReturn(mockDataJsonHandler);

    }

    @Test
    public void addPersonTest() throws IOException {
        Person newPersonToAdd = new Person("Clara", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "Clara@email.com");
        personRepository.addPerson(newPersonToAdd);

        List<Person> personsList = jsonFileHandler.readJsonFile().getPersons();

        // Vérifie si la personne ajoutée est bien dans la liste
        assertNotNull(personsList, "La liste des personnes n'est pas null");
        assertTrue(personsList.contains(newPersonToAdd), "La personne a bien été ajoutée correctement.");
    }

    @Test
    public void modifyFireStation() throws IOException, PersonNotFoundException {
        Person updatePerson = new Person("John", "Boyd", "new address", "new city", "11111", "111 111 1111", "Clara@email.com");

        personRepository.modifyPerson(updatePerson);

        Person modifiedPerson = mockDataJsonHandler.getPersons().stream()
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
        Person personToDelete = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");
        personRepository.deletePerson(personToDelete);

        List<Person> allPersons = jsonFileHandler.readJsonFile().getPersons();

        assertFalse(allPersons.contains(personToDelete));
    }
}
