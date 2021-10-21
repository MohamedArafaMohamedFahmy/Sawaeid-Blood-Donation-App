package com.arafa.mohamed.sawaeidblooddonation.models;

import java.io.Serializable;

public class DonorDataModel implements Serializable {
    private String name, city, phoneNumber, lastDonation, bloodType, notes,id;

    public  DonorDataModel(){

    }

    public DonorDataModel(String name, String city, String phoneNumber, String lastDonation, String bloodType, String notes, String id) {
        this.name = name;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.lastDonation = lastDonation;
        this.bloodType = bloodType;
        this.notes = notes;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
