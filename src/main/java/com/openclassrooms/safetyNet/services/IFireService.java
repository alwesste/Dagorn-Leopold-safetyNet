package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.result.FireHabitantDetails;

import java.io.IOException;
import java.util.List;

public interface IFireService {

    /**
     * Récupère la liste des détails des habitants d'une adresse donnée en cas d'incendie.
     *
     * @param address L'adresse pour laquelle on souhaite obtenir les détails des habitants affectés par l'incendie.
     * @return Une liste qui inclut le nom, le numéro de téléphone, l'âge et les antécédents médicaux.
     * @throws IOException Si une erreur se produit lors de la lecture des données ou de l'accès à la source de données.
     */
    List<FireHabitantDetails> getFireHabitantByAdress(String address) throws IOException;
}
