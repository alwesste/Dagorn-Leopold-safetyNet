package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.services.IPersonsService;
import com.openclassrooms.safetyNet.services.impl.PersonsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/person")
public class PersonsController {

    private static final Logger logger = LogManager.getLogger(PersonsController.class);

    private final IPersonsService personsService;

    public PersonsController(IPersonsService personsService) {
        this.personsService = personsService;
    }

    /**
     * Ajoute une nouvelle personne.
     *
     * @param persons L'objet Persons représentant la personne à ajouter.
     * @return L'objet Persons ajouté.
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Person addPerson(@RequestBody Person persons) {
        logger.info("Requête addPerson reçue: {}", persons);
        try {
            personsService.addPerson(persons);
            logger.info("Fin de la requete de creation de person: {}", persons );
        } catch (IOException e) {
            logger.error("erreur lors de l'ajout de la personne: {}", e.getMessage());
        }
        return persons;
    }

    /**
     * Modifie une personne existante.
     *
     * @param persons L'objet Persons avec les informations à mettre à jour.
     * @return L'objet Persons mis à jour.
     */
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping
    public Person modifyPerson(@RequestBody Person persons) {
        logger.info("Requête addPerson reçue: {}", persons);
        try {
            personsService.modifyPerson(persons);
            logger.info("modifyPerson modifiee: {}", persons);
        } catch (Exception e) {
            logger.error("La modification n'a pas eu lieu: {}", e.getMessage());
        }
        return persons;
    }

    /**
     * Supprime une personne.
     *
     * @param person L'objet Persons représentant la personne à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public boolean deletePerson(@RequestBody Person person) {
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
