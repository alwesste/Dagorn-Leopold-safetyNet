package com.openclassrooms.safetyNet.interfaces;

import com.openclassrooms.safetyNet.exceptions.MedicallRecordNotFoundException;
import com.openclassrooms.safetyNet.models.MedicalRecords;

import java.io.IOException;

public interface IMedicalRecordsService {
    void addMedicalRecord(MedicalRecords newMedicalRecord) throws IOException;
    void modifyMedicalRecord(MedicalRecords medicalRecordsModified) throws IOException;
    void deleteMedicalRecord(MedicalRecords medicalRecordsToDelete) throws MedicallRecordNotFoundException, IOException;
}
