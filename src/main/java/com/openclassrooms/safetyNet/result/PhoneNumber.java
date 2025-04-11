package com.openclassrooms.safetyNet.result;

public class PhoneNumber {
    private String phone;

    public PhoneNumber(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "phone='" + phone + '\'' +
                '}';
    }
}
