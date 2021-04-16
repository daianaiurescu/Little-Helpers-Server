package com.little.helpers.services;

import com.little.helpers.exceptions.EmailNotValid;
import com.little.helpers.exceptions.NotStrongPassword;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Encryption {

    public static void checkPasswordStrength (String password) throws NotStrongPassword {
        boolean cp1 = false, cp2 = false;
        if (password.length() < 8)
            throw new NotStrongPassword();
        char[] splitPassword = password.toCharArray();
        for (char c : splitPassword)
        {
            if (Character.isDigit(c))
                cp1 = true;
            if (Character.isUpperCase(c))
                cp2 = true;
        }
        if (!cp1 || !cp2)
            throw new NotStrongPassword();
    }

    public static void checkEmailStructure (String email) throws EmailNotValid {
        String emailRegularExpr = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (email.length() < 5)
            throw new EmailNotValid();
        Pattern patt = Pattern.compile(emailRegularExpr);
        if (!patt.matcher(email).matches() || email == null)
            throw new EmailNotValid();
    }

    public static String encryptString (String key, String password) {
        MessageDigest md = getMessageDigest();
        md.update(key.getBytes(StandardCharsets.UTF_8));
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return new String(hashedPassword, StandardCharsets.UTF_8).replace("\n", "");
    }
    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

}
