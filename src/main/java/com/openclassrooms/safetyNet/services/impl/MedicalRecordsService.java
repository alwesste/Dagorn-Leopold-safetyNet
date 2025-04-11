package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.exceptions.MedicallRecordNotFoundException;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.repository.MedicalRecordsRepository;
import com.openclassrooms.safetyNet.services.IMedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MedicalRecordsService implements IMedicalRecordsService {

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    @Override
    public void addMedicalRecord(MedicalRecords newMedicalRecord) throws IOException {
        medicalRecordsRepository.addMedicalRecord(newMedicalRecord);
    }

    @Override
    public void modifyMedicalRecord(MedicalRecords medicalRecordsModified) throws IOException {
        medicalRecordsRepository.modifyMedicalRecord(medicalRecordsModified);
    }

    @Override
    public void deleteMedicalRecord(MedicalRecords medicalRecordsToDelete) throws MedicallRecordNotFoundException, IOException {
        medicalRecordsRepository.deleteMedicalRecord(medicalRecordsToDelete);
    }
}
