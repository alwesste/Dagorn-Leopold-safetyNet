package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.exceptions.MedicallRecordNotFoundException;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordsService {

    private static final Logger logger = LogManager.getLogger(MedicalRecordsService.class);

    @Autowired
    private JsonFileHandler jsonFileHandler;

    public void addMedicalRecord(MedicalRecords newMedicalRecord) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<MedicalRecords> medicalRecordList = jsonFile.getMedicalrecords();

        try {
            medicalRecordList.add(newMedicalRecord);

        } catch (Exception e) {
            logger.error("L'ajout d'un nouveau suivi medical à échoué: {}", e.getMessage());
        }

        jsonFileHandler.writeJsonFile(jsonFile);
    }

    public void modifyMedicalRecord(MedicalRecords medicalRecordsModified) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<MedicalRecords> medicalRecordList = jsonFile.getMedicalrecords();

        Optional<MedicalRecords> recordToMofify = medicalRecordList.stream()
                .filter(medicalRecord ->
                        medicalRecord.getFirstName().equalsIgnoreCase(medicalRecordsModified.getFirstName()) &&
                        medicalRecord.getLastName().equalsIgnoreCase(medicalRecordsModified.getLastName()))
                .findFirst();

        if (recordToMofify.isPresent()) {
            MedicalRecords medicalRecord = recordToMofify.get();
            medicalRecord.setBirthdate(medicalRecordsModified.getBirthdate());
            medicalRecord.setMedications(medicalRecordsModified.getMedications());
            medicalRecord.setAllergies(medicalRecordsModified.getAllergies());
        }

        jsonFileHandler.writeJsonFile(jsonFile);
    }

    public void deleteMedicalRecord(MedicalRecords medicalRecordsToDelete) throws MedicallRecordNotFoundException, IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<MedicalRecords> medicalRecordList = jsonFile.getMedicalrecords();

        boolean isRemoved = medicalRecordList.removeIf(medicalRecords ->
                medicalRecords.getFirstName().equals(medicalRecordsToDelete.getFirstName()) &&
                        medicalRecords.getLastName().equals(medicalRecordsToDelete.getLastName())
        );

        if (!isRemoved) {
            throw new MedicallRecordNotFoundException("Personne introuvable avec le prénom et le nom fournis.");
        }
    }

}
