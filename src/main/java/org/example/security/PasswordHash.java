package org.example.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {

    public String createHashedPassword(String pass) throws NoSuchAlgorithmException {
        byte[] salt = new byte[16];
        MessageDigest messageDigest =
                MessageDigest.getInstance("SHA-512");
        messageDigest.update(salt);
        byte[] hashedPassword = messageDigest
                .digest(pass.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for (byte hs : hashedPassword) {
            stringBuilder.append(String.format("%02x", hs));
        }
        return stringBuilder.toString();
    }

}
