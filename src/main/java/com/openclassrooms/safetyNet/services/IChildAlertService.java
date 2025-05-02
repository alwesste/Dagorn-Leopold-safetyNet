package com.openclassrooms.safetyNet.services;
import com.openclassrooms.safetyNet.result.ChildAlert;

import java.io.IOException;
import java.util.List;

public interface IChildAlertService {

    /**
     * Récupère la liste des enfants vivant à une adresse donnée.
     *
     * @param address L'adresse pour laquelle on souhaite obtenir la liste des enfants.
     * @return Une liste d'enfants avec le nom de famille de chaque enfant, son âge et une liste des autres membres du foyer
     * @throws IOException Si une erreur se produit lors de la lecture des données ou de l'accès à la source de données.
     */
    List<ChildAlert> getListOfChild(String address) throws IOException;
}


