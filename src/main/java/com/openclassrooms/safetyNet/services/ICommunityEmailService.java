package com.openclassrooms.safetyNet.services;

import java.io.IOException;
import java.util.List;

public interface ICommunityEmailService {

    /**
     * Récupère la liste de toutes les adresses e-mail des habitants d'une ville donnée.
     *
     * @param city La ville pour laquelle on souhaite obtenir les adresses e-mail des habitants.
     * @return Une liste de chaînes de caractères contenant les adresses e-mail des habitants de la ville spécifiée.
     * @throws IOException Si une erreur se produit lors de la lecture des données ou de l'accès à la source de données.
     */
    List<String> getAllEmailFromCity(String city) throws IOException;
}
