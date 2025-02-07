package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.dtos.PersonDTO;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.services.PersonsService;
import org.apache.coyote.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonsController {

    private static final Logger logger = LogManager.getLogger(PersonsController.class);

    @Autowired
    private PersonsService personsService;

    @PostMapping
    public ResponseEntity<?> addPerson(@RequestBody Persons persons) {
        try {
            Persons newPerson = personsService.addPerson(persons);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
        } catch (Exception e) {
            logger.error("erreur lors de l'ajout de la personne: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("la requete n'a pas aboutie");
        }
    }

    @PutMapping
    public ResponseEntity<?> modifyPerson(@RequestBody Persons persons) {
        try {
            Persons personModified = personsService.modifyPerson(persons);
            return ResponseEntity.status(HttpStatus.OK).body(personModified);
        } catch (Exception e) {
            logger.error("La modification n'a pas eu lieu: {]", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erreur dans la modification");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletePerson(@RequestBody PersonDTO personsDTO) {
        try {
            Boolean personToDelete = personsService.deletePerson(personsDTO);
            return ResponseEntity.status(HttpStatus.OK).body("La personne à été supprime");
        } catch (Exception e) {
            logger.error("La suppression à échoué: {]", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur dans la suppression");
        }
    }
}
