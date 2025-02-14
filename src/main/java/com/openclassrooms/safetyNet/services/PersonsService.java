package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.dtos.PersonDTO;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Persons;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonsService {

    /**
     *
     * @param newPersonToAdd Represente la nouvelle personne a ajouter
     * @return la personne a ajouter (newPersonToAdd)
     * @throws IOException renvoie une erreur en cas probleme de lecture ou d'ecriture
     */
    public void addPerson(Persons newPersonToAdd) throws IOException {
        // Lire le fichier JSON et placer son contenu sous forme de map
        DataJsonHandler jsonFile = JsonFileHandler.readJsonFile();

        // Recuperer la cle "persons" sous forme de list
        List<Persons> personList = jsonFile.getPersons();

        // Genere une Map immuable dans le format attendu dans un fichier JSON
        personList.add(newPersonToAdd);

        // Ecriture du fichier JSON avec les changements
        JsonFileHandler.writeJsonFile(jsonFile);
    }

    /**
     *
     * @param personToModify represente la nouvelle personne a modifier
     * @throws IOException renvoie une erreur en cas probleme de lecture ou d'ecriture
     * @throws PersonNotFoundException renvoie une erreur si une personne
     */
    public void modifyPerson (Persons personToModify) throws IOException, PersonNotFoundException {
        DataJsonHandler jsonFile = JsonFileHandler.readJsonFile();
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

        JsonFileHandler.writeJsonFile(jsonFile);
    }

    /**
     *
     * @param personToDelete correspond a la personne à suprimmer
     * @return Un boolean si la suppression c'est bien deroulé
     * @throws PersonNotFoundException si le nom ou le prenom sont incorrect
     * @throws IOException si un probleme est survenu dans la suppresion
     */
    public void deletePerson(PersonDTO personToDelete) throws PersonNotFoundException, IOException {
        try {
            DataJsonHandler jsonFile = JsonFileHandler.readJsonFile();
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
            JsonFileHandler.writeJsonFile(jsonFile);

        } catch (IOException e) {
            throw new IOException("Erreur lors de la suppression de la personne", e);
        }
    }
}
