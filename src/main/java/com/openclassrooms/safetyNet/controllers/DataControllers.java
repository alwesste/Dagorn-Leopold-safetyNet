package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.result.*;
import com.openclassrooms.safetyNet.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class DataControllers {

    private static final Logger logger = LogManager.getLogger(DataControllers.class);

    @Autowired
    ChildAlertService childAlertService;

    @Autowired
    FirestationsService firestationsService;

    @Autowired
    private FireService fireService;

    @Autowired
    private FloodService floodService;

    @Autowired
    PersonLastNameSercice personLastNameSercice;

    @Autowired
    CommunityEmailService communityEmailService;

    @GetMapping("/childAlert")
    private List<ChildAlert> getChild(@RequestParam("address") String address) throws IOException {
        logger.info("Requête reçue pour obtenir les enfants couverts en fonctions des adresses: {}", address);
        logger.info("Requete recue");
        return childAlertService.getListOfChild(address);
    }

    @GetMapping("/phoneAlert")
    public List<PhoneNumber> getPhoneNumberFromStation(@RequestParam("firestation") int firestation) throws IOException {
        logger.info("Requête reçue pour obtenir numero de telephones couvert par la station numero: {}", firestation);
        logger.info("Requete recue");
        List<String> addresses = firestationsService.getAddressfromStationNumber(firestation);
        return firestationsService.getPhoneFromAddressFromStationNumber(addresses);
    }

    @GetMapping("/fire")
    public List<FireHabitantDetails> getFireHabitantByAddress(@RequestParam("address") String address) throws IOException {
        logger.info("Requete recue");
        return fireService.getFireHabitantByAdress(address);
    }

    @GetMapping("/flood/stations")
    public List<FloodHabitant> getFloodHabitant(@RequestParam("stations") List<String> stations) throws IOException {
        return floodService.getHomeByStation(stations);
    }

    @GetMapping("/personInfolastName")
    public List<PersonInfoLastnameDetail> getPersonInfoFromLastName(@RequestParam("lastName") String lastName) throws IOException {
        return personLastNameSercice.getPersonInfoFromLastName(lastName);
    }

    @GetMapping("/communityEmail")
    public List<String> getAllEmailFromCity(@RequestParam("city") String city) throws IOException {
        return communityEmailService.getAllEmailFromCity(city);
    }
}
