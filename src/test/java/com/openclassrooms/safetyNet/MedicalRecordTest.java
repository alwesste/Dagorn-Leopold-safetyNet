package com.openclassrooms.safetyNet;

import com.openclassrooms.safetyNet.exceptions.MedicallRecordNotFoundException;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.utils.JsonFileHandler;
import com.openclassrooms.safetyNet.services.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MedicalRecordTest {

    @InjectMocks
    MedicalRecordsService medicalRecordsService;

    @Mock
    JsonFileHandler jsonFileHandler;

    DataJsonHandler mockDataJsonHandler;

    @BeforeEach
    public void setUp() throws IOException {
        mockDataJsonHandler = new DataJsonHandler();

        MedicalRecords medicalRecordsOfJohn = new MedicalRecords("John", "Boyd", "03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList());
        List<MedicalRecords> medicalRecordsList = new ArrayList<>(List.of(medicalRecordsOfJohn));

        mockDataJsonHandler.setMedicalrecords(medicalRecordsList);

        when(jsonFileHandler.readJsonFile()).thenReturn(mockDataJsonHandler);
    }

    @Test
    public void addMedicalRecordTest() throws IOException {

        MedicalRecords medicalRecordsOfClara = new MedicalRecords("Clara", "Boyd", "04/11/2015",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of("allergies:illisoxian")
        );

        medicalRecordsService.addMedicalRecord(medicalRecordsOfClara);

        List<MedicalRecords> allMedicalRecord = mockDataJsonHandler.getMedicalrecords();
        assertNotNull(allMedicalRecord, "La liste ne doit pas etre null");
        assertTrue(allMedicalRecord.contains(medicalRecordsOfClara));
    }

    @Test
    public void modifyMedicalRecordTest() throws IOException {
        MedicalRecords newMedicalRecordsOfJohn = new MedicalRecords("John", "Boyd", "01/01/2000",
                Arrays.asList("pharmacolTest:5000mg", "terazineTest:10mg", "noznazolTest:250mg"),
                Collections.emptyList());

        medicalRecordsService.modifyMedicalRecord(newMedicalRecordsOfJohn);

        List<MedicalRecords> allMedicalRecord = mockDataJsonHandler.getMedicalrecords();
        assertNotNull(allMedicalRecord);

        Optional<MedicalRecords> updatedMedicalRecord = allMedicalRecord.stream()
                .filter(record -> "John".equals(record.getFirstName()) && "Boyd".equals(record.getLastName()))
                .findFirst();

        assertTrue(updatedMedicalRecord.isPresent(), "L'enregistrement modifié doit être présent");

        assertEquals("John", updatedMedicalRecord.get().getFirstName());
        assertEquals("Boyd", updatedMedicalRecord.get().getLastName());
        assertEquals("01/01/2000", updatedMedicalRecord.get().getBirthdate());
        assertEquals(Arrays.asList("pharmacolTest:5000mg", "terazineTest:10mg", "noznazolTest:250mg"), updatedMedicalRecord.get().getMedications());
    }

    @Test
    public void deleteMedicalRecodTest() throws IOException, MedicallRecordNotFoundException {
        MedicalRecords medicalRecordsOfJohnToDelete = new MedicalRecords("John", "Boyd", "03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList());

        medicalRecordsService.deleteMedicalRecord(medicalRecordsOfJohnToDelete);
        List<MedicalRecords> allMedicalRecord = mockDataJsonHandler.getMedicalrecords();

        assertFalse(allMedicalRecord.contains(medicalRecordsOfJohnToDelete));
    }

}
