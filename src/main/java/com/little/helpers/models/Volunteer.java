package com.little.helpers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "volunteers")
public class Volunteer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "applied_at")
    private String applied_at;

    public Volunteer(String firstName, String lastName, String bday, String phone, String emailAddress, String desc, String org) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = bday;
        this.phone = phone;
        this.email = emailAddress;
        description = desc;
        applied_at = org;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String bday) {
        birthday = bday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return email;
    }

    public void setEmailAddress(String emailAddress) {
        this.email = emailAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) { description = desc; }

    public String getApplied_at() {
        return applied_at;
    }

    public void setApplied_at(String org) {
        this.applied_at = org;
    }


}

