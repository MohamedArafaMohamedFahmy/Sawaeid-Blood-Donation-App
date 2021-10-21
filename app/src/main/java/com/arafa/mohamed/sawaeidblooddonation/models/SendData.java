package com.arafa.mohamed.sawaeidblooddonation.models;



public class SendData {

    private String name, city, phoneNumber, lastDonation, bloodType, notes;

    public  SendData(){

    }

    public SendData(String name, String city, String phoneNumber, String lastDonation, String bloodType) {
        this.name = name;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.lastDonation = lastDonation;
        this.bloodType = bloodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastDonation() {
        return lastDonation;
    }

    public void setLastDonation(String lastDonation) {
        this.lastDonation = lastDonation;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
