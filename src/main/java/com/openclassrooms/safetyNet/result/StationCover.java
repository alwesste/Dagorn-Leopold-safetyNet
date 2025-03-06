package com.openclassrooms.safetyNet.result;

import java.util.List;

public class StationCover {
    private List<PersonInformation> persons;
    private int adultsCount;
    private int childrenCount;

    public StationCover(List<PersonInformation> persons, int adultsCount, int childrenCount) {
        this.persons = persons;
        this.adultsCount = adultsCount;
        this.childrenCount = childrenCount;
    }

    // Getters
    public List<PersonInformation> getPersons() {
        return persons;
    }

    public int getAdultsCount() {
        return adultsCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }
}

