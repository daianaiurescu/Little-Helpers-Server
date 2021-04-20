package com.little.helpers.exceptions;

public class NotStrongPassword extends Exception {
    public NotStrongPassword() {
        super("Password needs at least one uppercase character and one digit, minimum length is 8");
    }
}
