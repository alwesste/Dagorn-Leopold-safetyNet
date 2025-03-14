package com.openclassrooms.safetyNet.controllers;


import com.openclassrooms.safetyNet.result.PersonInfoLastname;
import com.openclassrooms.safetyNet.services.PersonLastNameSercice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/personInfolastName")
public class PersonsLastNameControllers {

    private static final Logger logger = LogManager.getLogger(PersonsLastNameControllers.class);

    @Autowired
    PersonLastNameSercice personLastNameSercice;

    @GetMapping
    public PersonInfoLastname getPersonInfoFromLastName(@RequestParam("lastName") String lastName) throws IOException {
        return personLastNameSercice.getPersonInfoFromLastName(lastName);
    }
}
