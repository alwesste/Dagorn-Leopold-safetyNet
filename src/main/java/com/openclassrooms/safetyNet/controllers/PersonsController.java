package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.dtos.PersonDTO;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.services.PersonsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonsController {

    private static final Logger logger = LogManager.getLogger(PersonsController.class);

    @Autowired
    private PersonsService personsService;

    @PostMapping
    public Persons addPerson(@RequestBody Persons persons) {
        try {
            personsService.addPerson(persons);
        } catch (Exception e) {
            logger.error("erreur lors de l'ajout de la personne: {}", e.getMessage());
        }
        return persons;
    }

    @PutMapping
    public Persons modifyPerson(@RequestBody Persons persons) {
        try {
           personsService.modifyPerson(persons);
        } catch (Exception e) {
            logger.error("La modification n'a pas eu lieu: {}", e.getMessage());
        }
        return persons;
    }

    @DeleteMapping
    public boolean deletePerson(@RequestBody PersonDTO personsDTO) {
        try {
            personsService.deletePerson(personsDTO);
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }
}
