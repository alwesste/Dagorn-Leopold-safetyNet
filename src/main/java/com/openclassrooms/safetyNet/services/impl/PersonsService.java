package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.repository.PersonRepository;
import com.openclassrooms.safetyNet.services.IPersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PersonsService implements IPersonsService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public void addPerson(Person newPersonToAdd) throws IOException {
        personRepository.addPerson(newPersonToAdd);
    }

    @Override
    public void modifyPerson(Person personToModify) throws IOException, PersonNotFoundException {
        personRepository.modifyPerson(personToModify);
    }

    @Override
    public void deletePerson(Person personToDelete) throws PersonNotFoundException, IOException {
        personRepository.deletePerson(personToDelete);
    }
}
