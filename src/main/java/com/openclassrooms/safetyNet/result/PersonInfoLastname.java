package com.openclassrooms.safetyNet.result;

import java.util.List;

public class PersonInfoLastname {
    List<PersonInfoLastnameDetail> personInfoLastnameDetails;

    public PersonInfoLastname() {
    }

    public PersonInfoLastname(List<PersonInfoLastnameDetail> personInfoLastnameDetails) {
        this.personInfoLastnameDetails = personInfoLastnameDetails;
    }

    public List<PersonInfoLastnameDetail> getPersonInfoLastnameDetails() {
        return personInfoLastnameDetails;
    }

    public void setPersonInfoLastnameDetails(List<PersonInfoLastnameDetail> personInfoLastnameDetails) {
        this.personInfoLastnameDetails = personInfoLastnameDetails;
    }
}
