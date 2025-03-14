package com.openclassrooms.safetyNet;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.services.FirestationsService;
import com.openclassrooms.safetyNet.services.JsonFileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

//    @Mock
//    private JsonFileHandler mockJsonFileHandler;
//
//    @Autowired
//    private FirestationsService firestationsService;
//
//    @Test
//    public void getAddressFromStationNumberTest() throws IOException {
//        // Simuler les données du fichier JSON
//        DataJsonHandler dataJsonHandler = new DataJsonHandler("123 Main St", "1");
//
//        List<Firestations> firestationsList = Arrays.asList(firestation1, firestation2, firestation3);
//
//        // Mock du comportement de JsonFileHandler.readJsonFile()
//        when(mockJsonFileHandler.readJsonFile()).thenReturn();
//
//        // Appel de la méthode à tester
//        List<String> addresses = firestationsService.getAddressfromStationNumber(1);
//
//        // Vérifications
//        assertEquals(2, addresses.size());
//        assertTrue(addresses.contains("123 Main St"));
//        assertTrue(addresses.contains("456 Oak St"));
//        assertFalse(addresses.contains("789 Pine St"));
//
//    }


    @Mock
    private JsonFileHandler mockJsonFileHandler;

    @InjectMocks
    private FirestationsService firestationsService;

    private List<Firestations> firestationsList;

    @BeforeEach
    void setUp() {
        firestationsList = Arrays.asList(
                new Firestations("123 Main St", "1"),
                new Firestations("456 Oak St", "1"),
                new Firestations("789 Pine St", "2")
        );
    }

    @Test
    public void getAddressFromStationNumberTest() throws IOException {
        // Simuler les données du fichier JSON
        DataJsonHandler dataJsonHandler = new DataJsonHandler();
        dataJsonHandler.setFirestations(firestationsList);

        // Mock du comportement de JsonFileHandler.readJsonFile()
        when(mockJsonFileHandler.readJsonFile()).thenReturn(dataJsonHandler);

        // Appel de la méthode à tester
        List<String> addresses = firestationsService.getAddressfromStationNumber(1);

        // Vérifications
        assertNotNull(addresses);
        assertTrue(addresses.contains("123 Main St"));
        assertTrue(addresses.contains("456 Oak St"));
        assertFalse(addresses.contains("789 Pine St"));
    }
}