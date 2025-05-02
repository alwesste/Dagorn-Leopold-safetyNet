package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.models.MedicalRecord;
import com.openclassrooms.safetyNet.services.IMedicalRecordsService;
import com.openclassrooms.safetyNet.services.impl.MedicalRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordsController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordsController.class);

    private final IMedicalRecordsService medicalRecordsService;

    public MedicalRecordsController(IMedicalRecordsService medicalRecordsService) {
        this.medicalRecordsService = medicalRecordsService;
    }

    /**
     *
     * @param medicalRecords L'objet MedicalRecords à ajouter
     * @return Le dossier médical ajouté.
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecords) {
        logger.info("Requete addMedicalRecord reçue: {}", medicalRecords);
        try {
            medicalRecordsService.addMedicalRecord(medicalRecords);
            logger.info("Fin de la requete pour ajouter un medicalRecord: {}", medicalRecords);
        } catch (IOException e) {
            logger.error("Erreur lors de la création du nouveau medicalRecord: {}", e.getMessage());
        }
        return medicalRecords;
    }

    /**
     *
     * @param medicalRecordToModify L'objet MedicalRecords contenant les nouvelles informations.
     * @return Le dossier médical modifié.
     */
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping
    public MedicalRecord modifyMedicalRecord(@RequestBody MedicalRecord medicalRecordToModify) {
        logger.info("Requete modifyMedicalRecord reçue: {}", medicalRecordToModify);
        try {
            medicalRecordsService.modifyMedicalRecord(medicalRecordToModify);
            logger.info("addMedicalRecord modifie");
        } catch (Exception e) {
            logger.error("Erreur lors de la modification du MedicalRecord : {}", e.getMessage());
        }
        return medicalRecordToModify;
    }

    /**
     *
     * @param medicalRecordToDelete L'objet MedicalRecords à supprimer.
     * @return true si la suppression a réussi.
     */
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public boolean deleteMedicalRecord(@RequestBody MedicalRecord medicalRecordToDelete) {
        logger.info("Requete deleteMedicalRecord reçue: {}", medicalRecordToDelete);
        try {
            medicalRecordsService.deleteMedicalRecord(medicalRecordToDelete);
            logger.info("MedicalRecord supprime");
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }

}
