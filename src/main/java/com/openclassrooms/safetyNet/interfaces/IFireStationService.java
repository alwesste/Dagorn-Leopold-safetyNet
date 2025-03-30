package com.openclassrooms.safetyNet.interfaces;

import com.openclassrooms.safetyNet.exceptions.FirestationNotFoundException;
import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.result.PhoneNumber;
import com.openclassrooms.safetyNet.result.StationCover;

import java.io.IOException;
import java.util.List;

public interface IFireStationService {
    List<StationCover> getCoverPersons(int stationNumber) throws IOException;

    List<String> getAddressesByStation(List<Firestations> fireStations, int stationNumber);

    List<Persons> getPersonsByAddresses(List<Persons> persons, List<String> addresses);

    List<String> getAddressfromStationNumber(int firestation) throws IOException;

    List<PhoneNumber> getPhoneFromAddressFromStationNumber(List<String> stationAddress) throws IOException;

    void addFireStation(Firestations newFirestations) throws IOException;

    void modifyFireStation(Firestations firestationModified) throws IOException;

    void deleteStation(Firestations firestationsToDelete) throws IOException, FirestationNotFoundException;
}