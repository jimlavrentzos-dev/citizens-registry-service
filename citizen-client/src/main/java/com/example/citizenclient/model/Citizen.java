package com.example.citizenclient.model;

public class Citizen {
    private String at;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
    private String afm;
    private String address;

    // Getters & Setters
    public String getAt() { return at; }
    public void setAt(String at) { this.at = at; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getAfm() { return afm; }
    public void setAfm(String afm) { this.afm = afm; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}