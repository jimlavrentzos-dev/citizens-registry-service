package com.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "citizens")
public class Citizen {

    @Id
    @Size(min = 8, max = 8)
    private String id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String gender;

    @NotBlank
    private String birthDate;

    @Pattern(regexp = "^[0-9]{9}$", message = "Ο ΑΦΜ του πολίτη θα πρέπει απαραίτητα να έχει 9 ψηφία")
    private String afm;

    private String address;

    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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
