package com.mmd.auth.mapper;

import com.mmd.application.dto.LoginDto;
import com.mmd.auth.request.AuthRequest;
import com.mmd.member.dto.MemberCreateDto;
import com.mmd.member.dto.MemberUpdateDto;
import com.mmd.member.request.MemberRequest;

public class ServiceDtoMapper {

    public static LoginDto mapping(AuthRequest.LoginRequest request) {
        return new LoginDto(
                request.getEmail(),
                request.getPassword()
        );
    }

}
