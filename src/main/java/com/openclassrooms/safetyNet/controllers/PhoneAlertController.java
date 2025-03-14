package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.result.PhoneNumber;
import com.openclassrooms.safetyNet.services.FirestationsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PhoneAlertController {

    private static final Logger logger = LogManager.getLogger(PhoneAlertController.class);

    @Autowired
    FirestationsService firestationsService;

    @GetMapping("/phoneAlert")
    public List<PhoneNumber> getPhoneNumberFromStation(@RequestParam("firestation") int firestation) throws IOException {
        logger.info("Requête reçue pour obtenir numero de telephones couvert par la station numero: {}", firestation);
        logger.info("Requete recue");
        List<String> addresses = firestationsService.getAddressfromStationNumber(firestation);
        return firestationsService.getPhoneFromAddressFromStationNumber(addresses);
    }

}
