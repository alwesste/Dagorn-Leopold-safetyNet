package com.openclassrooms.safetyNet.models;

public class Firestation {
    private String address;
    private String station;

    public Firestation() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Firestation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public String toString() {
        return "Firestations{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
