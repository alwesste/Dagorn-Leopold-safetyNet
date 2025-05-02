package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.result.*;
import com.openclassrooms.safetyNet.services.*;
import com.openclassrooms.safetyNet.services.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class DataController {

    private static final Logger logger = LogManager.getLogger(DataController.class);
    private final IChildAlertService childAlertService;
    private final IFireStationService firestationsService;
    private final IFireService fireService;
    private final IFloodService floodService;
    private final IPersonLastNameSercice personLastNameSercice;
    private final ICommunityEmailService communityEmailService;

    public DataController(IChildAlertService childAlertService, IFireStationService firestationsService, IFireService fireService, IFloodService floodService, IPersonLastNameSercice personLastNameSercice, ICommunityEmailService communityEmailService) {
        this.childAlertService = childAlertService;
        this.firestationsService = firestationsService;
        this.fireService = fireService;
        this.floodService = floodService;
        this.personLastNameSercice = personLastNameSercice;
        this.communityEmailService = communityEmailService;
    }

    /**
     *
     * @param address a verifier
     * @return une liste d'enfants avec le nom de famille de chaque enfant, son âge et une liste des autres membres du foyer
     * @throws IOException en cas d'erreur lors de l'accès aux données
     */
    @GetMapping("/childAlert")
    public List<ChildAlert> getChild(@RequestParam("address") String address) throws IOException {
        return childAlertService.getListOfChild(address);
    }

    /**
     *
     * @param firestation, numero de caserne
     * @return une liste de numero de telephone
     * @throws IOException en cas d'erreur lors de l'accès aux données
     */
    @GetMapping("/phoneAlert")
    public List<PhoneNumber> getPhoneNumberFromStation(@RequestParam("firestation") int firestation) throws IOException {
        logger.info("Requête reçue pour obtenir numero de telephones couvert par la station numero: {}", firestation);
        logger.info("Requete recue");
        List<String> addresses = firestationsService.getAddressfromStationNumber(firestation);
        return firestationsService.getPhoneFromAddressFromStationNumber(addresses);
    }

    /**
     *
     * @param address a verifier
     * @return Une liste qui inclut le nom, le numéro de téléphone, l'âge et les antécédents médicaux.
     * @throws IOException en cas d'erreur lors de l'accès aux données
     */
    @GetMapping("/fire")
    public List<FireHabitantDetails> getFireHabitantByAddress(@RequestParam("address") String address) throws IOException {
        logger.info("Requete recue avec comme adresse : {}", address);
        return fireService.getFireHabitantByAdress(address);
    }

    /**
     *
     * @param stations a verifier
     * @return une liste qui regroupe les personnes par adresse. Elle inclut le nom, le
     *  numéro de téléphone et l'âge des habitants, et faire figurer leurs antécédents médicaux
     * @throws IOException en cas d'erreur lors de l'accès aux données
     */
    @GetMapping("/flood/stations")
    public List<FloodHabitant> getFloodHabitant(@RequestParam("stations") List<String> stations) throws IOException {
        return floodService.getHomeByStation(stations);
    }

    /**
     *
     * @param lastName, nom de famille
     * @return une liste avec nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux
     * @throws IOException en cas d'erreur lors de l'accès aux données
     */
    @GetMapping("/personInfolastName")
    public List<PersonInfoLastnameDetail> getPersonInfoFromLastName(@RequestParam("lastName") String lastName) throws IOException {
        return personLastNameSercice.getPersonInfoFromLastName(lastName);
    }

    /**
     *
     * @param city, nom de la ville
     * @return une des adresses email des personnes concernes.
     * @throws IOException en cas d'erreur lors de l'accès aux données
     */
    @GetMapping("/communityEmail")
    public List<String> getAllEmailFromCity(@RequestParam("city") String city) throws IOException {
        return communityEmailService.getAllEmailFromCity(city);
    }
}
