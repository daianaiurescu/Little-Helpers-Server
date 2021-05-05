package com.little.helpers.exceptions;

public class UserTokenNotFound extends RuntimeException{
    public UserTokenNotFound(String e) {
        super(e);
    }
}
