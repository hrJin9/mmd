package com.mmd.member.mapper;

import com.mmd.application.dto.LoginDto;
import com.mmd.member.dto.MemberCreateDto;
import com.mmd.member.dto.MemberUpdateDto;
import com.mmd.member.request.AuthRequest;
import com.mmd.member.request.MemberRequest;

public class ServiceDtoMapper {

    public static LoginDto mapping(AuthRequest.LoginRequest request) {
        return new LoginDto(
                request.getEmail(),
                request.getPassword()
        );
    }

    public static MemberCreateDto mapping(MemberRequest.MemberCreateRequest request) {
        return new MemberCreateDto(
                request.getMemberId(),
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhone(),
                request.getAddress()
        );
    }

    public static MemberUpdateDto mapping(String memberId, MemberRequest.MemberUpdateRequest request) {
        return new MemberUpdateDto(
                memberId,
                request.getName(),
                request.getPhone(),
                request.getAddress()
        );
    }

}
