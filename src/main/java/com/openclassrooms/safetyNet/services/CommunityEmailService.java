package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.utils.JsonFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CommunityEmailService {

    @Autowired
    JsonFileHandler jsonFileHandler;

    public List<String> getAllEmailFromCity(String city) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        return jsonFile.getPersons().stream()
                .filter(persons -> persons.getCity().equalsIgnoreCase(city))
                .map(Persons::getEmail)
                .distinct()
                .toList();
    }

}
