package com.todos.mmd.auth.application.util;

import com.todos.mmd.global.exception.PasswordBadRequestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$");

    public static void validatePassword(String password) {
        Matcher matcher = pattern.matcher(password);
        if(!matcher.find()) {
            throw new PasswordBadRequestException("비밀번호 형식을 다시 확인해 주세요.");
        }
    }


}
