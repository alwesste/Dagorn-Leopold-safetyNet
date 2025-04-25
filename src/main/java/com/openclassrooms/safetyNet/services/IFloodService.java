package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.result.FloodHabitant;

import java.io.IOException;
import java.util.List;

public interface IFloodService {

    /**
     * Récupère la liste des habitants par foyer (adresse) desservis par une ou plusieurs casernes de pompiers.
     *
     * @param stations Liste des numéros de casernes de pompiers.
     * @return Une liste d'objets FloodHabitant contenant les informations sur les foyers et leurs habitants.
     * @throws IOException En cas de problème de lecture des données (par exemple, fichier introuvable ou corrompu).
     */
    List<FloodHabitant> getHomeByStation(List<String> stations) throws IOException;
}
