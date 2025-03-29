package com.openclassrooms.safetyNet.result;

public class StationCover {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private int age;
    private int adultsCount;
    private int childrenCount;

    public StationCover(String firstName, String lastName, String address, String phone, int age, int adultsCount, int childrenCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.adultsCount = adultsCount;
        this.childrenCount = childrenCount;
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

    public int getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }
}

