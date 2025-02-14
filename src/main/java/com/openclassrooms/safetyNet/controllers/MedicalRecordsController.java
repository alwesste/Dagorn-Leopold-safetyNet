package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.services.MedicalRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordsController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordsController.class);

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @PostMapping
    public MedicalRecords addMedicalRecord(@RequestBody MedicalRecords medicalRecords) {
        try {
            medicalRecordsService.addMedicalRecord(medicalRecords);
        } catch (IOException e) {
            logger.error("Erreur lors de la création du nouveau medicalRecord: {}", e.getMessage());
        }
        return medicalRecords;
    }

    @PutMapping
    public MedicalRecords modifyMedicalRecord(@RequestBody MedicalRecords medicalRecordToModify) {
        try {
            medicalRecordsService.modifyMedicalRecord(medicalRecordToModify);
        } catch (Exception e) {
            logger.error("Erreur lors de la modification du MedicalRecord : {}", e.getMessage());
        }
        return medicalRecordToModify;
    }

    @DeleteMapping
    public boolean deleteStation(@RequestBody MedicalRecords medicalRecordToDelete) {
        try {
            medicalRecordsService.deleteMedicalRecord(medicalRecordToDelete);
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }

}
