package com.little.helpers.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Users {
    @Id
    private long id;
    private String email_address;
    private String password;
    private String role;
    public Users(String mail, String pass, String role){
        email_address=mail;
        password=pass;
        this.role=role;
    }
    public void setId(long id){
        this.id=id;
    }
    public void setEmail_address(String email){
        email_address=email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setRole(String role){
        this.role=role;
    }
    public long getId(){
        return id;
    }
    public String getEmail_address(){
        return email_address;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", email_address='" + email_address + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
