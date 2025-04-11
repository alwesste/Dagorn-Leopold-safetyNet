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
     * @param newPersonToAdd Represente la nouvelle personne a ajouter
     * @throws IOException renvoie une erreur en cas probleme de lecture ou d'ecriture
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
     * @param personToModify represente la nouvelle personne a modifier
     * @throws IOException             renvoie une erreur en cas probleme de lecture ou d'ecriture
     * @throws PersonNotFoundException renvoie une erreur si une personne
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
     * @param personToDelete correspond a la personne à suprimmer
     * @return Un boolean si la suppression c'est bien deroulé
     * @throws PersonNotFoundException si le nom ou le prenom sont incorrect
     * @throws IOException             si un probleme est survenu dans la suppresion
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
