package com.openclassrooms.safetyNet.result;

import java.util.List;

public class FireHabitant {
    private List<PersonDetails> person;

    public FireHabitant(List<PersonDetails> person) {
        this.person = person;
    }

    public List<PersonDetails> getPerson() {
        return person;
    }

    public void setPerson(List<PersonDetails> person) {
        this.person = person;
    }
}


