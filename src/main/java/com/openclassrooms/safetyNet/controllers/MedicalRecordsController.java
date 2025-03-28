package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.services.MedicalRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordsController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordsController.class);

    private final MedicalRecordsService medicalRecordsService;

    public MedicalRecordsController(MedicalRecordsService medicalRecordsService) {
        this.medicalRecordsService = medicalRecordsService;
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public MedicalRecords addMedicalRecord(@RequestBody MedicalRecords medicalRecords) {
        try {
            logger.info("Requete addMedicalRecord reçue");
            medicalRecordsService.addMedicalRecord(medicalRecords);
            logger.info("addMedicalRecord créée");
        } catch (IOException e) {
            logger.error("Erreur lors de la création du nouveau medicalRecord: {}", e.getMessage());
        }
        return medicalRecords;
    }

    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping
    public MedicalRecords modifyMedicalRecord(@RequestBody MedicalRecords medicalRecordToModify) {
        try {
            logger.info("Requete modifyMedicalRecord reçue");
            medicalRecordsService.modifyMedicalRecord(medicalRecordToModify);
            logger.info("addMedicalRecord modifie");
        } catch (Exception e) {
            logger.error("Erreur lors de la modification du MedicalRecord : {}", e.getMessage());
        }
        return medicalRecordToModify;
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public boolean deleteMedicalRecord(@RequestBody MedicalRecords medicalRecordToDelete) {
        try {
            logger.info("Requete deleteMedicalRecord reçue");
            medicalRecordsService.deleteMedicalRecord(medicalRecordToDelete);
            logger.info("MedicalRecord supprime");
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }

}
