package com.openclassrooms.safetyNet.result;

import java.util.List;

public class FloodHabitant {
    private String firstname;
    private String lastname;
    private String phone;
    private int age;
    private List<MedicalHistory> medicalHistories;

    public FloodHabitant(String firstname, String lastname, String phone, int age, List<MedicalHistory> medicalHistories) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.age = age;
        this.medicalHistories = medicalHistories;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<MedicalHistory> getMedicalHistories() {
        return medicalHistories;
    }

    public void setMedicalHistories(List<MedicalHistory> medicalHistories) {
        this.medicalHistories = medicalHistories;
    }
}
