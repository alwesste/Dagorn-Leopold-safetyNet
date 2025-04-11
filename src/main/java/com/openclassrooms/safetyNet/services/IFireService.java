package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.result.FireHabitantDetails;

import java.io.IOException;
import java.util.List;

public interface IFireService {
    List<FireHabitantDetails> getFireHabitantByAdress(String address) throws IOException;
}
