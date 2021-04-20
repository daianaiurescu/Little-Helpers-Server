package com.little.helpers.exceptions;

public class EmailAlreadyExists extends Exception{
    public EmailAlreadyExists (String email) {
        super ("Email: " + email + " already exists");
    }
}
