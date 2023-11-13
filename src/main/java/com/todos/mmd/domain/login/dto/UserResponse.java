package com.todos.mmd.domain.login.dto;

import com.todos.mmd.domain.model.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private String email;

    public static UserResponse toVO(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.email = user.getEmail();
        return userResponse;
    }
}
