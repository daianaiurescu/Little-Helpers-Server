package com.little.helpers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.util.List;
@Data
@ToString
public class UserPublic {
    private int id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String role;

    private String token;

    public UserPublic(int id, String firstName, String lastName, String emailAddress, String role, String token) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.role = role;
        this.token = token;
    }
}
