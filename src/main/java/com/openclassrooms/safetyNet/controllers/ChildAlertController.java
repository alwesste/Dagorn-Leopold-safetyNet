package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.result.ChildAlert;
import com.openclassrooms.safetyNet.services.ChildAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertController {

    private static final Logger logger = LogManager.getLogger(ChildAlertController.class);

    @Autowired
    ChildAlertService childAlertService;

    @GetMapping
    private ChildAlert getChild(@RequestParam("address") String address) throws IOException {
        logger.info("Requête reçue pour obtenir les enfants couverts en fonctions des adresses: {}", address);
        logger.info("Requete recue");
        return childAlertService.getListOfChild(address);
    }
}
