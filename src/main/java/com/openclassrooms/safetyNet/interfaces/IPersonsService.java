package com.openclassrooms.safetyNet.interfaces;

import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.models.Persons;

import java.io.IOException;

public interface IPersonsService {
    void addPerson(Persons newPersonToAdd) throws IOException;
    void modifyPerson(Persons personToModify) throws IOException, PersonNotFoundException;
    void deletePerson(Persons personToDelete) throws PersonNotFoundException, IOException;
}
