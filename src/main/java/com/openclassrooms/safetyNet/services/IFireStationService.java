package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.exceptions.FirestationNotFoundException;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.result.PhoneNumber;
import com.openclassrooms.safetyNet.result.StationCover;

import java.io.IOException;
import java.util.List;

public interface IFireStationService {
    List<StationCover> getCoverPersons(int stationNumber) throws IOException;

    List<String> getAddressesByStation(List<Firestation> fireStations, int stationNumber);

    List<Persons> getPersonsByAddresses(List<Persons> persons, List<String> addresses);

    List<String> getAddressfromStationNumber(int firestation) throws IOException;

    List<PhoneNumber> getPhoneFromAddressFromStationNumber(List<String> stationAddress) throws IOException;

    void addFireStation(Firestation newFirestation) throws IOException;

    void modifyFireStation(Firestation firestationModified) throws IOException;

    void deleteStation(Firestation firestationToDelete) throws IOException, FirestationNotFoundException;
}