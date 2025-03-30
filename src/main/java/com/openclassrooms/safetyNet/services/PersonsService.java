package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.interfaces.IPersonsService;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.utils.JsonFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonsService implements IPersonsService {

    @Autowired
    JsonFileHandler jsonFileHandler;

    /**
     * @param newPersonToAdd Represente la nouvelle personne a ajouter
     * @throws IOException renvoie une erreur en cas probleme de lecture ou d'ecriture
     */
    @Override
    public void addPerson(Persons newPersonToAdd) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<Persons> personList = jsonFile.getPersons();

        personList.add(newPersonToAdd);

        jsonFileHandler.writeJsonFile(jsonFile);
    }

    /**
     * @param personToModify represente la nouvelle personne a modifier
     * @throws IOException             renvoie une erreur en cas probleme de lecture ou d'ecriture
     * @throws PersonNotFoundException renvoie une erreur si une personne
     */
    @Override
    public void modifyPerson(Persons personToModify) throws IOException, PersonNotFoundException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Persons> personList = jsonFile.getPersons();

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

        jsonFileHandler.writeJsonFile(jsonFile);
    }

    /**
     * @param personToDelete correspond a la personne à suprimmer
     * @return Un boolean si la suppression c'est bien deroulé
     * @throws PersonNotFoundException si le nom ou le prenom sont incorrect
     * @throws IOException             si un probleme est survenu dans la suppresion
     */
    @Override
    public void deletePerson(Persons personToDelete) throws PersonNotFoundException, IOException {
        try {
            DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
            List<Persons> personList = jsonFile.getPersons();

            // Supprimer la personne correspondant au prénom et au nom
            boolean isRemoved = personList.removeIf(person ->
                    person.getFirstName().equals(personToDelete.getFirstName()) &&
                            person.getLastName().equals(personToDelete.getLastName())
            );

            if (!isRemoved) {
                throw new PersonNotFoundException("Personne introuvable avec le prénom et le nom fournis.");
            }

            // Écrire dans le fichier uniquement si une suppression a eu lieu
            jsonFileHandler.writeJsonFile(jsonFile);

        } catch (IOException e) {
            throw new IOException("Erreur lors de la suppression de la personne", e);
        }
    }
}
