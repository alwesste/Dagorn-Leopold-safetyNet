package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.services.CommunityEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    @Autowired
    CommunityEmailService communityEmailService;

    @GetMapping
    public List<String> getAllEmailFromCity(@RequestParam("city") String city) throws IOException {
        return communityEmailService.getAllEmailFromCity(city);
    }
}
