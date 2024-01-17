package com.mmd.member;

import com.mmd.member.mapper.ServiceDtoMapper;
import com.mmd.member.request.MemberRequest;
import com.mmd.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /* 일반회원 회원가입 */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid MemberRequest.MemberCreateRequest request){
        memberService.register(ServiceDtoMapper.mapping(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /* 일반회원 정보 수정 */
    @PutMapping
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal MemberDetails memberDetails,
                                             @RequestBody @Valid MemberRequest.MemberUpdateRequest request
    ) {
        memberService.updateMember(ServiceDtoMapper.mapping(memberDetails.getId(), request));
        return ResponseEntity.ok().build();
    }
}
