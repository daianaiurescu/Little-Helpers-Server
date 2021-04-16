package com.little.helpers.exceptions;

public class IncorrectPassword extends Exception {
    public IncorrectPassword () {
        super ("Password is incorrect");
    }
}
