package com.mmd.member;

import com.mmd.member.mapper.ServiceDtoMapper;
import com.mmd.member.request.MemberRequest;
import com.mmd.security.MemberDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "회원 API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "일반회원 회원가입", notes = "일반 회원이 회원가입합니다.", tags = "회원 API")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid MemberRequest.MemberCreateRequest request){
        memberService.register(ServiceDtoMapper.mapping(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @ApiOperation(value = "일반회원 정보 수정", notes = "일반 회원의 회원 정보를 수정합니다.", tags = "회원 API")
    @PutMapping
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal MemberDetails memberDetails,
                                             @RequestBody @Valid MemberRequest.MemberUpdateRequest request
    ) {
        memberService.updateMember(ServiceDtoMapper.mapping(memberDetails.getId(), request));
        return ResponseEntity.ok().build();
    }
}
