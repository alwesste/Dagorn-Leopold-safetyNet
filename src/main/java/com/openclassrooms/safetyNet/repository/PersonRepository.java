package com.openclassrooms.safetyNet.repository;

import com.openclassrooms.safetyNet.exceptions.PersonAlreadyExistException;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Persons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    private final Logger logger = LogManager.getLogger(PersonRepository.class);

    @Autowired
    IJsonFileHandler jsonFileHandler;

    /**
     * Ajoute une nouvelle personne si elle n'existe pas déjà dans le fichier JSON.
     *
     * @param newPersonToAdd La nouvelle personne à ajouter.
     * @throws IOException Si une erreur d'accès au fichier JSON survient.
     * @throws PersonAlreadyExistException Si une personne avec le même prénom et nom existe déjà.
     */
    public void addPerson(Persons newPersonToAdd) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<Persons> personList = jsonFile.getPersons();
        logger.debug("La liste de personne existante {}", personList);

        boolean personAlreadyExist = personList.stream()
                .anyMatch(person -> person.getFirstName().equalsIgnoreCase(newPersonToAdd.getFirstName()) &&
                        person.getLastName().equalsIgnoreCase(newPersonToAdd.getLastName()));

        if (personAlreadyExist) {
            logger.warn("La personne existe déjà dans data.json: {} {}", newPersonToAdd.getFirstName(), newPersonToAdd.getLastName());
            throw new PersonAlreadyExistException("Cette personne est deja enregistrer avec nom et prenom");
        }

        personList.add(newPersonToAdd);
        logger.debug("Person a ajouter: {}", newPersonToAdd);
        jsonFileHandler.writeJsonFile(jsonFile);
    }

    /**
     * Modifie les informations d'une personne existante dans le fichier JSON.
     *
     * @param personToModify La personne à modifier, avec les nouvelles informations.
     * @throws IOException Si une erreur d'accès au fichier JSON survient.
     * @throws PersonNotFoundException Si la personne à modifier n'est pas trouvée.
     */
    public void modifyPerson(Persons personToModify) throws IOException, PersonNotFoundException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Persons> personList = jsonFile.getPersons();
        logger.debug("La liste de personne existante {}", personList);


        Optional<Persons> persons = personList.stream()
                .filter(person ->
                        person.getFirstName().equalsIgnoreCase(personToModify.getFirstName()) &&
                                person.getLastName().equalsIgnoreCase(personToModify.getLastName()))
                .findFirst();

        if (persons.isPresent()) {
            Persons person = persons.get();
            person.setAddress(personToModify.getAddress());
            person.setCity(personToModify.getCity());
            person.setZip(personToModify.getZip());
            person.setPhone(personToModify.getPhone());
            person.setEmail(personToModify.getEmail());
        }
        logger.debug("Personne a modifier {} ", personToModify);

        jsonFileHandler.writeJsonFile(jsonFile);
    }

    /**
     * Supprime une personne du fichier JSON en fonction de son prénom et nom.
     *
     * @param personToDelete La personne à supprimer.
     * @throws PersonNotFoundException Si la personne à supprimer n'est pas trouvée.
     * @throws IOException Si une erreur d'accès au fichier JSON survient.
     */
    public void deletePerson(Persons personToDelete) throws PersonNotFoundException, IOException {
        try {
            DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
            logger.debug("La liste de personne existante {}", personToDelete);

            List<Persons> personList = jsonFile.getPersons();

            boolean isRemoved = personList.removeIf(person ->
                    person.getFirstName().equals(personToDelete.getFirstName()) &&
                            person.getLastName().equals(personToDelete.getLastName())
            );

            if (!isRemoved) {
                throw new PersonNotFoundException("Personne introuvable avec le prénom et le nom fournis.");
            }
            logger.debug("Peronne a supprimer {}", personToDelete);

            jsonFileHandler.writeJsonFile(jsonFile);

        } catch (IOException e) {
            throw new IOException("Erreur lors de la suppression de la personne", e);
        }
    }
}
