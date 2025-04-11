package com.openclassrooms.safetyNet.services;

public interface ICalculateAgeService {

    /**
     * Calcule l'âge d'une personne à partir de sa date de naissance.
     *
     * @param birthdate La date de naissance au format "yyyy-MM-dd".
     * @return L'âge de la personne en années
     */
    public int calculateAge(String birthdate);
}
