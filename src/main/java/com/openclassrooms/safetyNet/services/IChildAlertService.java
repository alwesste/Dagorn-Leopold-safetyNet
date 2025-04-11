package com.openclassrooms.safetyNet.services;
import com.openclassrooms.safetyNet.result.ChildAlert;

import java.io.IOException;
import java.util.List;

public interface IChildAlertService {
    List<ChildAlert> getListOfChild(String address) throws IOException;
}


