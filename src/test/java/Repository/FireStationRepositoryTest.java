//package Repository;
//
//import com.openclassrooms.safetyNet.SafetyNetApplication;
//import com.openclassrooms.safetyNet.exceptions.FirestationNotFoundException;
//import com.openclassrooms.safetyNet.models.DataJsonHandler;
//import com.openclassrooms.safetyNet.models.Firestations;
//import com.openclassrooms.safetyNet.models.MedicalRecords;
//import com.openclassrooms.safetyNet.models.Persons;
//import com.openclassrooms.safetyNet.repository.FirestationRepository;
//import com.openclassrooms.safetyNet.result.StationCover;
//import com.openclassrooms.safetyNet.services.impl.CalculateAgeService;
//import com.openclassrooms.safetyNet.repository.JsonFileHandler;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest(classes = SafetyNetApplication.class)
//@ExtendWith(MockitoExtension.class)
//public class FireStationRepositoryTest {
//
//    @Mock
//    private JsonFileHandler jsonFileHandler;
//
//    @Mock
//    private CalculateAgeService calculateAgeService;
//
//    @InjectMocks
//    private FirestationRepository firestationRepository;
//
//    DataJsonHandler mockdataJsonHandler;
//
//    @BeforeEach
//    void setUp() throws IOException {
//        mockdataJsonHandler = new DataJsonHandler();
//
//        Firestations firestations1 = new Firestations("1509 Culver St", "1");
//        Firestations firestations2 = new Firestations("456 Oak St", "2");
//        List<Firestations> firestationsList = new ArrayList<>(List.of(firestations1, firestations2));
//
//        Persons personJohn = new Persons("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");
//        Persons personClara = new Persons("Clara", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "Clara@email.com");
//        Persons personPaul = new Persons("Paul", "steph", "1509 Culver St", "Culver", "97451", "841-874-6512", "Paul@email.com");
//        Persons personSophie = new Persons("Sophie", "Rodrigez", "1509 Culver St", "Culver", "97451", "841-874-6512", "Sophie@email.com");
//        Persons personCharle = new Persons("Charle", "Pastoi", "1509 Culver St", "Culver", "97451", "841-874-6512", "Charle@email.com");
//        List<Persons> personsList = new ArrayList<>(List.of(personJohn, personClara, personPaul, personSophie, personCharle));
//
//        MedicalRecords medicalRecordsOfJohn = new MedicalRecords("John","Boyd","03/06/2020",
//                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
//                Collections.emptyList()
//        );
//        MedicalRecords medicalRecordsOfClara = new MedicalRecords("Clara","Boyd","04/11/2015",
//                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
//                List.of("allergies:illisoxian")
//        );
//        MedicalRecords medicalRecordsOfPaul = new MedicalRecords("Paul","steph","03/06/1980",
//                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
//                List.of("allergies:illisoxian")
//        );
//        MedicalRecords medicalRecordsOfSophie = new MedicalRecords("Sophie","Rodrigez","03/06/1999",
//                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
//                List.of("allergies:illisoxian")
//        );
//        MedicalRecords medicalRecordsOfCharle = new MedicalRecords("Charle","Pastoi","03/06/2000",
//                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
//                List.of("allergies:illisoxian")
//        );
//
//        mockdataJsonHandler.setFirestations(firestationsList);
//        mockdataJsonHandler.setPersons(personsList);
//        mockdataJsonHandler.setMedicalrecords(List.of(medicalRecordsOfCharle, medicalRecordsOfClara, medicalRecordsOfJohn, medicalRecordsOfSophie, medicalRecordsOfPaul));
//        when(jsonFileHandler.readJsonFile()).thenReturn(mockdataJsonHandler);
//    }
//
//    @Test
//    public void getCoverPersonTest() throws IOException {
//
//        when(calculateAgeService.calculateAge("03/06/2020")).thenReturn(5);
//        when(calculateAgeService.calculateAge("04/11/2015")).thenReturn(10);
//        when(calculateAgeService.calculateAge("03/06/1980")).thenReturn(41);
//        when(calculateAgeService.calculateAge("03/06/1999")).thenReturn(22);
//        when(calculateAgeService.calculateAge("03/06/2000")).thenReturn(21);
//
//        List<StationCover> result = firestationRepository.getCoverPersons(1);
//
//        assertNotNull(result);
//        System.out.println(result);
//        assertEquals(3, result.getFirst().getAdultsCount());
//        assertEquals(2, result.getFirst().getChildrenCount());
//
//        StationCover stationCover = result.stream()
//                .filter(sc -> sc.getFirstName().equals("John") && sc.getLastName().equals("Boyd"))
//                .findFirst()
//                .orElse(null);
//
//        assertNotNull(stationCover, "John Boyd devrait être dans la liste.");
//        assertEquals("1509 Culver St", stationCover.getAddress());
//        assertEquals("841-874-6512", stationCover.getPhone());
//        assertEquals(5, stationCover.getAge());
//    }
//
//    @Test
//    public void addFireStationTest() throws IOException {
//        Firestations firestationsToAdd = new Firestations("111 test", "3");
//
//        firestationRepository.addFireStation(firestationsToAdd);
//        List<Firestations> allFirestations = jsonFileHandler.readJsonFile().getFirestations();
//
//        assertNotNull(allFirestations, "La liste des casernes n'est pas null");
//        assertTrue(allFirestations.contains(firestationsToAdd), "La caserne n'a pas été ajoutée correctement.");
//
//        Firestations addedFirestation = allFirestations.stream()
//                .filter(firestation -> "111 test".equals(firestation.getAddress()) && "3".equals(firestation.getStation()))
//                .findFirst()
//                .orElse(null);
//
//        assertNotNull(addedFirestation, "La caserne ajoutée devrait être présente.");
//        assertEquals("111 test", addedFirestation.getAddress(), "L'adresse de la caserne ajoutée est incorrecte.");
//        assertEquals("3", addedFirestation.getStation(), "Le numéro de la station ajouté est incorrect.");
//    }
//
//    @Test
//    public void modifyFireStationTest() throws IOException {
//
//        Firestations newfireStation = new Firestations("456 Oak St", "3");
//        firestationRepository.modifyFireStation(newfireStation);
//
//        List<Firestations> allFirestations = jsonFileHandler.readJsonFile().getFirestations();
//
//        assertNotNull(allFirestations, "La liste des casernes ne doit pas être nulle.");
//
//        assertFalse(allFirestations.stream()
//                        .anyMatch(firestation -> "456 Oak St".equals(firestation.getAddress()) && "2".equals(firestation.getStation())),
//                "L'ancienne station 2 ne doit plus exister a cette adresse."
//        );
//
//        assertTrue(allFirestations.stream()
//                        .anyMatch(firestation -> firestation.getAddress().equalsIgnoreCase("456 Oak St") && firestation.getStation().equalsIgnoreCase("3")),
//                "La station doit être mise à jour avec le numéro 3."
//        );
//    }
//
//    @Test
//    public void deleteStationTest() throws IOException, FirestationNotFoundException {
//
//        Firestations deleletedStation = new Firestations("1509 Culver St", "1");
//        firestationRepository.deleteStation(deleletedStation);
//
//        List<Firestations> allFirestations = jsonFileHandler.readJsonFile().getFirestations();
//
//        assertFalse(allFirestations.contains(deleletedStation), "La caserne n'a pas été supprimer correctement.");
//
//    }
//}
