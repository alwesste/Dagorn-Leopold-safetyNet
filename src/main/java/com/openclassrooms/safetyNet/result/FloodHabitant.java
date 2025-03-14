package com.openclassrooms.safetyNet.result;

import java.util.List;

public class FloodHabitant {
    List<HomeDetails> homeDetails;

    public FloodHabitant(List<HomeDetails> homeDetails) {
        this.homeDetails = homeDetails;
    }

    public List<HomeDetails> getHomeDetails() {
        return homeDetails;
    }

    public void setHomeDetails(List<HomeDetails> homeDetails) {
        this.homeDetails = homeDetails;
    }
}
