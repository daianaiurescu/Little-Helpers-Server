package com.little.helpers.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFound extends UsernameNotFoundException {
    public UserNotFound() {
        super("No user exists with these email");
    }
}
