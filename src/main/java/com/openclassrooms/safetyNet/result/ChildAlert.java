package com.openclassrooms.safetyNet.result;

import java.util.List;

public class ChildAlert {
    String firstName;
    String lastName;
    int age;
    List<FamilyMember> familyMembers;

    public ChildAlert(String firstName, List<FamilyMember> familyMembers, int age, String lastName) {
        this.firstName = firstName;
        this.familyMembers = familyMembers;
        this.age = age;
        this.lastName = lastName;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }
}
