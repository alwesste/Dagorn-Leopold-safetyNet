package com.openclassrooms.safetyNet.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class CalculateAgeService {

    public int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }}
