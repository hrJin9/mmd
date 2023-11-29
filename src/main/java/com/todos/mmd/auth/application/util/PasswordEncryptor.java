package com.todos.mmd.auth.application.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncryptor {

    private static final String ALGORITHM = "SHA-256";

    public static String encrypt(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(password.getBytes());

            return Base64.getEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("존재하지 않는 암호화 알고리즘입니다.");
        }
    }

}
