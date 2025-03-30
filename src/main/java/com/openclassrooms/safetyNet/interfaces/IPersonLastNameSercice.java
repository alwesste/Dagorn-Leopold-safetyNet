package com.openclassrooms.safetyNet.interfaces;

import com.openclassrooms.safetyNet.result.PersonInfoLastnameDetail;

import java.io.IOException;
import java.util.List;

public interface IPersonLastNameSercice {
    List<PersonInfoLastnameDetail> getPersonInfoFromLastName(String lastName) throws IOException;
}
