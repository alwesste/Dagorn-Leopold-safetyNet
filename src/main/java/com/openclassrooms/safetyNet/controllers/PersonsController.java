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
        logger.info("Requête addPerson reçue: {}", persons);
        try {
            personsService.addPerson(persons);
            logger.info("addPerson créée: {}", persons );
        } catch (Exception e) {
            logger.error("erreur lors de l'ajout de la personne: {}", e.getMessage());
        }
        return persons;
    }

    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping
    public Persons modifyPerson(@RequestBody Persons persons) {
        logger.info("Requête addPerson reçue: {}", persons);
        try {
            personsService.modifyPerson(persons);
            logger.info("modifyPerson modifiee: {}", persons);
        } catch (Exception e) {
            logger.error("La modification n'a pas eu lieu: {}", e.getMessage());
        }
        return persons;
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public boolean deletePerson(@RequestBody Persons person) {
        logger.info("Requête addPerson reçue: {}", person);
        try {
            personsService.deletePerson(person);
            logger.info("Person supprime: {}", person);
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }
}
