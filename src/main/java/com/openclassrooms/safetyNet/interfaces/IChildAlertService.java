package com.openclassrooms.safetyNet.interfaces;
import com.openclassrooms.safetyNet.result.ChildAlert;

import java.io.IOException;
import java.util.List;

public interface IChildAlertService {
    List<ChildAlert> getListOfChild(String address) throws IOException;
}


