package com.openclassrooms.safetyNet.repository;

import com.openclassrooms.safetyNet.exceptions.MedicalRecordExistException;
import com.openclassrooms.safetyNet.exceptions.MedicallRecordNotFoundException;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecord;
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

    /**
     * Ajoute un nouveau dossier médical si aucune correspondance prénom/nom n'existe déjà.
     *
     * @param newMedicalRecord Le dossier médical à ajouter.
     * @throws IOException, Si une erreur d'accès au fichier JSON survient.
     * @throws MedicalRecordExistException, Si un dossier médical avec le même prénom et nom existe déjà.
     */
    public void addMedicalRecord(MedicalRecord newMedicalRecord) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<MedicalRecord> medicalRecordList = jsonFile.getMedicalrecords();
        logger.debug("Nouveau MedicalRecord à ajouter : {}", newMedicalRecord);

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

    /**
     * Modifie un dossier médical existant en mettant à jour la date de naissance, les médicaments et les allergies.
     *
     * @param medicalRecordsModified Le dossier médical modifié à enregistrer.
     * @throws IOException Si une erreur d'accès au fichier JSON survient.
     */
    public void modifyMedicalRecord(MedicalRecord medicalRecordsModified) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<MedicalRecord> medicalRecordList = jsonFile.getMedicalrecords();
        logger.debug("MedicalRecord modifie: {}", medicalRecordsModified);

        Optional<MedicalRecord> recordToMofify = medicalRecordList.stream()
                .filter(medicalRecord ->
                        medicalRecord.getFirstName().equalsIgnoreCase(medicalRecordsModified.getFirstName()) &&
                        medicalRecord.getLastName().equalsIgnoreCase(medicalRecordsModified.getLastName()))
                .findFirst();

        if (recordToMofify.isPresent()) {
            MedicalRecord medicalRecord = recordToMofify.get();
            medicalRecord.setBirthdate(medicalRecordsModified.getBirthdate());
            medicalRecord.setMedications(medicalRecordsModified.getMedications());
            medicalRecord.setAllergies(medicalRecordsModified.getAllergies());
        }

        jsonFileHandler.writeJsonFile(jsonFile);
    }

    /**
     * Supprime un dossier médical en fonction du prénom et du nom fournis.
     *
     * @param medicalRecordsToDelete Le dossier médical à supprimer.
     * @throws MedicallRecordNotFoundException Si aucun dossier ne correspond au prénom et nom donnés.
     * @throws IOException, Si une erreur d'accès au fichier JSON survient.
     */
    public void deleteMedicalRecord(MedicalRecord medicalRecordsToDelete) throws MedicallRecordNotFoundException, IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        logger.debug("MedicalRecord to delete: {}", medicalRecordsToDelete);

        List<MedicalRecord> medicalRecordList = jsonFile.getMedicalrecords();
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
