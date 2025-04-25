package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.exceptions.MedicallRecordNotFoundException;
import com.openclassrooms.safetyNet.models.MedicalRecord;

import java.io.IOException;

public interface IMedicalRecordsService {

    /**
     * Ajoute un nouveau dossier médical.
     *
     * @param newMedicalRecord Le dossier médical à ajouter.
     * @throws IOException En cas d'erreur d'entrée/sortie (par exemple : lecture/écriture de fichier).
     */
    void addMedicalRecord(MedicalRecord newMedicalRecord) throws IOException;

    /**
     * Modifie un dossier médical existant.
     *
     * @param medicalRecordsModified Le dossier médical contenant les modifications à appliquer.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    void modifyMedicalRecord(MedicalRecord medicalRecordsModified) throws IOException;

    /**
     * Supprime un dossier médical existant.
     *
     * @param medicalRecordsToDelete Le dossier médical à supprimer.
     * @throws MedicallRecordNotFoundException Si le dossier médical à supprimer n'existe pas.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    void deleteMedicalRecord(MedicalRecord medicalRecordsToDelete) throws MedicallRecordNotFoundException, IOException;
}
