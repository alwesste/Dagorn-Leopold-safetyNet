package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.result.PersonInfoLastnameDetail;

import java.io.IOException;
import java.util.List;

public interface IPersonLastNameSercice {

    /**
     * Récupère les informations détaillées des personnes correspondant à un nom de famille donné.
     *
     * @param lastName Le nom de famille utilisé pour la recherche.
     * @return Une liste d'objets PersonInfoLastnameDetail contenant les informations détaillées (âge, adresse, antécédents médicaux, etc.).
     * @throws IOException En cas de problème lors de l'accès aux données (ex. : fichier introuvable ou erreur de lecture).
     */
    List<PersonInfoLastnameDetail> getPersonInfoFromLastName(String lastName) throws IOException;
}
