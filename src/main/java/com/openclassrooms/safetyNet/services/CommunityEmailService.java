package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.interfaces.ICommunityEmailService;
import com.openclassrooms.safetyNet.interfaces.IJsonFileHandler;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CommunityEmailService implements ICommunityEmailService {

    @Autowired
    IJsonFileHandler jsonFileHandler;

    @Override
    public List<String> getAllEmailFromCity(String city) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        return jsonFile.getPersons().stream()
                .filter(persons -> persons.getCity().equalsIgnoreCase(city))
                .map(Persons::getEmail)
                .distinct()
                .toList();
    }

}
