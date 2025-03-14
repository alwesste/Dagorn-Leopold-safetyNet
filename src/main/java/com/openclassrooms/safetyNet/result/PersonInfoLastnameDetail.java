package com.openclassrooms.safetyNet.result;

import java.util.List;

public class PersonInfoLastnameDetail {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<MedicalHistory> medicalHistories;

    public PersonInfoLastnameDetail() {
    }

    public PersonInfoLastnameDetail(String firstName, String lastName, String address, int age, String email, List<MedicalHistory> medicalHistories) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.email = email;
        this.medicalHistories = medicalHistories;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MedicalHistory> getMedicalHistories() {
        return medicalHistories;
    }

    public void setMedicalHistories(List<MedicalHistory> medicalHistories) {
        this.medicalHistories = medicalHistories;
    }
}
