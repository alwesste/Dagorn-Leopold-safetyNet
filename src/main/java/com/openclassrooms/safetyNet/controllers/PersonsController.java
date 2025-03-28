package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.services.PersonsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonsController {

    private static final Logger logger = LogManager.getLogger(PersonsController.class);

    private final PersonsService personsService;

    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Persons addPerson(@RequestBody Persons persons) {
        try {
            logger.info("Requete addPerson reçue");
            personsService.addPerson(persons);
            logger.info("addPerson créée");
        } catch (Exception e) {
            logger.error("erreur lors de l'ajout de la personne: {}", e.getMessage());
        }
        return persons;
    }

    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping
    public Persons modifyPerson(@RequestBody Persons persons) {
        try {
            logger.info("Requete modifyPerson reçue");
            personsService.modifyPerson(persons);
            logger.info("modifyPerson modifiee");
        } catch (Exception e) {
            logger.error("La modification n'a pas eu lieu: {}", e.getMessage());
        }
        return persons;
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public boolean deletePerson(@RequestBody Persons person) {
        try {
            logger.info("Requete deletePerson reçue");
            personsService.deletePerson(person);
            logger.info("Person supprime");
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }
}
