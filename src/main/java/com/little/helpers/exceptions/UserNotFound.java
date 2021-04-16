package com.little.helpers.exceptions;

public class UserNotFound extends Exception{
    public UserNotFound() {
        super("No user exists with these email");
    }
}
