package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.exceptions.MedicallRecordNotFoundException;
import com.openclassrooms.safetyNet.models.MedicalRecord;
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
    public void addMedicalRecord(MedicalRecord newMedicalRecord) throws IOException {
        medicalRecordsRepository.addMedicalRecord(newMedicalRecord);
    }

    @Override
    public void modifyMedicalRecord(MedicalRecord medicalRecordsModified) throws IOException {
        medicalRecordsRepository.modifyMedicalRecord(medicalRecordsModified);
    }

    @Override
    public void deleteMedicalRecord(MedicalRecord medicalRecordsToDelete) throws MedicallRecordNotFoundException, IOException {
        medicalRecordsRepository.deleteMedicalRecord(medicalRecordsToDelete);
    }
}
