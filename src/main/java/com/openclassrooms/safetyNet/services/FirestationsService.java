package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.models.Firestations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class FirestationsService {

    @Autowired
    private JsonFileHandler jsonFileHandler;


    public Firestations addFireStation(Firestations newFirestations) throws IOException {
        Map<String, Object> jsonFile = jsonFileHandler.readJsonFile();
        List<Map<String, Object>> fireStationList = (List<Map<String, Object>>) jsonFile.get("firestations");

        if(fireStationList != null) {
            fireStationList.add(Map.of(
                "address", newFirestations.getAddress(),
                "station", newFirestations.getStation()
            ));
        }
        jsonFileHandler.writeJsonFile(jsonFile);
        return newFirestations;
    }



}
