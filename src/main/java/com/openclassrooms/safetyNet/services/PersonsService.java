package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.dtos.PersonDTO;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.models.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PersonsService {

    /**
     *
     * @param newPersonToAdd Represente la nouvelle personne a ajouter
     * @return la personne a ajouter (newPersonToAdd)
     * @throws IOException renvoie une erreur en cas probleme de lecture ou d'ecriture
     */
    public Persons addPerson(Persons newPersonToAdd) throws IOException {
        // Lire le fichier JSON et placer son contenu sous forme de map
        Map<String, Object> jsonFile = JsonFileHandler.readJsonFile();

        // Recuperer la cle "persons" sous forme de list
        List<Map<String, Object>> personList = (List<Map<String, Object>>) jsonFile.get("persons");

        // verifier si personList existe
        if(personList != null) {
            // Genere une Map immuable dans le format attendu dans un fichier JSON
            personList.add(Map.of(
                    "firstName", newPersonToAdd.getFirstName(),
                    "lastName", newPersonToAdd.getLastName(),
                    "address", newPersonToAdd.getAddress(),
                    "city", newPersonToAdd.getCity(),
                    "zip", newPersonToAdd.getZip(),
                    "phone", newPersonToAdd.getPhone(),
                    "email", newPersonToAdd. getEmail()
            ));
        }

        // Ecriture du fichier JSON avec les changements
        JsonFileHandler.writeJsonFile(jsonFile);

        return newPersonToAdd;
    }

    /**
     *
     * @param personToModify represente la      nouvelle personne a modifier
     * @return la personne a modifier les parametres
     * @throws IOException renvoie une erreur en cas probleme de lecture ou d'ecriture
     * @throws PersonNotFoundException renvoie une erreur si une personne
     */
    public Persons modifyPerson (Persons personToModify) throws IOException, PersonNotFoundException {
        Map<String, Object> jsonFile = JsonFileHandler.readJsonFile();
        List<Map<String, Object>> personList = (List<Map<String, Object>>) jsonFile.get("persons");

        if (personList !=null) {
            for (Map<String, Object> person: personList) {
                if (
                    person.get("firstName").equals(personToModify.getFirstName()) &&
                    person.get("lastName").equals(personToModify.getLastName())) {
                        person.put("address", personToModify.getAddress());
                        person.put("city", personToModify.getCity());
                        person.put("zip", personToModify.getZip());
                        person.put("phone", personToModify.getPhone());
                        person.put("email", personToModify.getEmail());
                }

                JsonFileHandler.writeJsonFile(jsonFile);
            }
        } else {
            throw new PersonNotFoundException("Le nom ou prenom est incorrect");
        }

        return personToModify;
    }

    /**
     *
     * @param firstName correspond au prénom recherche dans le fichierJSON
     * @param lastName correspond au nom de famille dans le fichier JSON
     * @return Un boolean si la suppression c'est bien deroulé
     * @throws PersonNotFoundException si le nom ou le prenom sont incorrect
     * @throws IOException si un probleme est survenu dans la suppresion
     */
    public boolean deletePerson(PersonDTO personToDelete) throws PersonNotFoundException, IOException {
        try {
            Map<String, Object> jsonFile = JsonFileHandler.readJsonFile();
            List<Map<String, Object>> personList = (List<Map<String, Object>>) jsonFile.get("persons");

            if (personList == null) {
                throw new PersonNotFoundException("La liste des personnes est vide ou introuvable.");
            }

            // Supprimer la personne correspondant au prénom et au nom
            boolean isRemoved = personList.removeIf(person ->
                    person.get("firstName").equals(personToDelete.getFirstName()) && person.get("lastName").equals(personToDelete.getLastName())
            );

            if (!isRemoved) {
                throw new PersonNotFoundException("Personne introuvable avec le prénom et le nom fournis.");
            }

            // Écrire dans le fichier uniquement si une suppression a eu lieu
            JsonFileHandler.writeJsonFile(jsonFile);

            return true;
        } catch (IOException e) {
            throw new IOException("Erreur lors de la lecture ou de l'écriture du fichier JSON.", e);
        }
    }
}
