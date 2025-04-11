package com.openclassrooms.safetyNet.repository;

import com.openclassrooms.safetyNet.exceptions.MedicalRecordExistException;
import com.openclassrooms.safetyNet.exceptions.MedicallRecordNotFoundException;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.services.impl.MedicalRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicalRecordsRepository{

    private static final Logger logger = LogManager.getLogger(MedicalRecordsService.class);

    @Autowired
    private IJsonFileHandler jsonFileHandler;


    public void addMedicalRecord(MedicalRecords newMedicalRecord) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<MedicalRecords> medicalRecordList = jsonFile.getMedicalrecords();
        logger.debug("NewMedicalRecord : {}", newMedicalRecord);

        boolean medicalRecordAlreadyExist = medicalRecordList.stream()
                .anyMatch(medicalRecords -> medicalRecords.getFirstName().equalsIgnoreCase(newMedicalRecord.getFirstName()) &&
                        medicalRecords.getLastName().equalsIgnoreCase(newMedicalRecord.getLastName()));

        if (medicalRecordAlreadyExist) {
            logger.warn("Le medicalRecord existe déjà dans data.json: {} {}", newMedicalRecord.getFirstName(), newMedicalRecord.getLastName());
            throw new MedicalRecordExistException("Erreur lors de l'ajout. Le medicalRecord existe deja avec ce nom et prenom");
        }

        medicalRecordList.add(newMedicalRecord);

        jsonFileHandler.writeJsonFile(jsonFile);
    }

    public void modifyMedicalRecord(MedicalRecords medicalRecordsModified) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<MedicalRecords> medicalRecordList = jsonFile.getMedicalrecords();
        logger.debug("MedicalRecord modifie: {}", medicalRecordsModified);

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
        logger.debug("MedicalRecord to delete: {}", medicalRecordsToDelete);

        List<MedicalRecords> medicalRecordList = jsonFile.getMedicalrecords();
        logger.debug("Liste des medicalRecord present : {}", medicalRecordList);

        boolean isRemoved = medicalRecordList.removeIf(medicalRecords ->
                medicalRecords.getFirstName().equals(medicalRecordsToDelete.getFirstName()) &&
                        medicalRecords.getLastName().equals(medicalRecordsToDelete.getLastName())
        );

        if (!isRemoved) {
            throw new MedicallRecordNotFoundException("MedicalRecord introuvable avec le prénom et le nom fournis.");
        }

        jsonFileHandler.writeJsonFile(jsonFile);
    }

}
