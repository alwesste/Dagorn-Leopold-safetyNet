package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.models.Person;

import java.io.IOException;

public interface IPersonsService {

    /**
     * Ajoute une nouvelle personne dans le système.
     *
     * @param newPersonToAdd La personne à ajouter.
     * @throws IOException En cas de problème lors de l'accès aux données (fichier ou base de données).
     */
    void addPerson(Person newPersonToAdd) throws IOException;

    /**
     * Modifie les informations d'une personne existante.
     *
     * @param personToModify La personne avec les informations mises à jour.
     * @throws IOException En cas de problème lors de l'accès aux données.
     * @throws PersonNotFoundException Si la personne à modifier n'existe pas dans le système.
     */
    void modifyPerson(Person personToModify) throws IOException, PersonNotFoundException;

    /**
     * Supprime une personne du système.
     *
     * @param personToDelete La personne à supprimer.
     * @throws PersonNotFoundException Si la personne à supprimer n'existe pas.
     * @throws IOException En cas de problème lors de la suppression des données.
     */
    void deletePerson(Person personToDelete) throws PersonNotFoundException, IOException;
}
