//package com.mmd.application.dto;
//
//import com.mmd.api.request.AuthRequest;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//
//@Getter
//@RequiredArgsConstructor
//public class AdminCreateDto {
//    private final String email;
//    private final String password;
//    private final String name;
//
//    public static AdminCreateDto from(AuthRequest.AdminCreateRequest request) {
//        return new AdminCreateDto(
//                request.getEmail(),
//                request.getPassword(),
//                request.getName()
//        );
//    }
//
//}
