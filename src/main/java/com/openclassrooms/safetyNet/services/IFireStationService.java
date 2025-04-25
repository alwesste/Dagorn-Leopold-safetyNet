package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.exceptions.FirestationNotFoundException;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.result.PhoneNumber;
import com.openclassrooms.safetyNet.result.StationCover;

import java.io.IOException;
import java.util.List;

/**
 * Interface définissant les services relatifs aux stations de pompiers et à la gestion des informations associées.
 * Cette interface permet d'obtenir des informations sur les stations de pompiers, les habitants couvert par ces stations,
 * ainsi que la gestion des stations de pompiers dans le système. Elle inclut des méthodes pour obtenir des listes de personnes,
 * des adresses, et des numéros de téléphone associés aux zones couvertes par une station.
 */
public interface IFireStationService {

    /**
     * Récupère la liste des personnes couvertes par une station de pompiers spécifique.
     *
     * @param stationNumber Le numéro de la station de pompiers pour laquelle on souhaite obtenir la liste des personnes couvertes.
     * @return une liste de StationCover, nom des personnes couvertes par la caserne de pompiers avec l'adresse, numéro de téléphone,
     * ainsi que le decompte du nombre d'adultes et du nombre d'enfants
     * @throws IOException Si une erreur se produit lors de la lecture des données ou de l'accès à la source de données.
     */
    List<StationCover> getCoverPersons(int stationNumber) throws IOException;

    /**
     * Récupère les adresses couvertes par une station de pompiers donnée.
     *
     * @param fireStations La liste des stations de pompiers
     * @param stationNumber Le numéro de la station pour laquelle on souhaite obtenir les adresses couvertes.
     * @return Une liste de chaînes de caractères représentant les adresses couvertes par la station spécifiée.
     */
    List<String> getAddressesByStation(List<Firestation> fireStations, int stationNumber);

    /**
     * Récupère les personnes résidant à une liste d'adresses spécifiées.
     *
     * @param persons La liste des personnes dans le système.
     * @param addresses La liste des adresses pour lesquelles les informations des personnes doivent être récupérées.
     * @return Une liste de personne correspondant aux adresses spécifiées.
     */
    List<Person> getPersonsByAddresses(List<Person> persons, List<String> addresses);


    /**
     * Récupère la liste des adresses couvertes par une station de pompiers spécifique.
     *
     * @param firestation Le numéro de la station de pompiers pour laquelle on souhaite obtenir les adresses couvertes.
     * @return Une liste de chaînes de caractères représentant les adresses couvertes par la station spécifiée.
     * @throws IOException Si une erreur se produit lors de la lecture des données ou de l'accès à la source de données.
     */
    List<String> getAddressfromStationNumber(int firestation) throws IOException;

    /**
     * Récupère les numéros de téléphone des personnes habitant à une liste d'adresses couvertes par une station de pompiers.
     *
     * @param stationAddress La liste des adresses couvertes par la station de pompiers pour lesquelles on souhaite obtenir les numéros de téléphone.
     * @return Une liste de contenant les numéros de téléphone des personnes vivant aux adresses spécifiées.
     * @throws IOException Si une erreur se produit lors de la lecture des données ou de l'accès à la source de données.
     */
    List<PhoneNumber> getPhoneFromAddressFromStationNumber(List<String> stationAddress) throws IOException;

    /**
     * Ajoute une nouvelle station de pompiers au système.
     *
     * @param newFirestation La station de pompiers à ajouter.
     * @throws IOException Si une erreur se produit lors de la lecture ou de l'écriture des données.
     */
    void addFireStation(Firestation newFirestation) throws IOException;

    /**
     * Modifie les informations d'une station de pompiers existante dans le système.
     *
     * @param firestationModified La station de pompiers avec les informations mises à jour.
     * @throws IOException Si une erreur se produit lors de la lecture ou de l'écriture des données.
     */
    void modifyFireStation(Firestation firestationModified) throws IOException;

    /**
     * Supprime une station de pompiers du système.
     *
     * @param firestationToDelete La station de pompiers à supprimer.
     * @throws IOException Si une erreur se produit lors de la lecture ou de l'écriture des données.
     * @throws FirestationNotFoundException Si la station de pompiers spécifiée n'est pas trouvée dans le système.
     */
    void deleteStation(Firestation firestationToDelete) throws IOException, FirestationNotFoundException;
}