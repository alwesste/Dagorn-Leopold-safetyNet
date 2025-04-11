package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.services.ICalculateAgeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class CalculateAgeService implements ICalculateAgeService {

    /**
     * Calcule l'âge d'une personne à partir de sa date de naissance.
     *
     * @param birthdate La date de naissance au format "yyyy-MM-dd".
     * @return L'âge de la personne en années
     */
    @Override
    public int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }}
