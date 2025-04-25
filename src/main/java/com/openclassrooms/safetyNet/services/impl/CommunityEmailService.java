package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.services.ICommunityEmailService;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CommunityEmailService implements ICommunityEmailService {

    private final Logger logger = LogManager.getLogger(CommunityEmailService.class);

    @Autowired
    IJsonFileHandler jsonFileHandler;

    @Override
    public List<String> getAllEmailFromCity(String city) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        logger.debug("Donnees lues par jsonFileHandler,  nombre de personnes récupérées : {}", jsonFile.getPersons().size());

        List<String> emails = jsonFile.getPersons().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .toList();

        logger.debug("Liste d'emails distincts extraits pour la ville {} : {}", city, emails);

        return emails;
    }

}
