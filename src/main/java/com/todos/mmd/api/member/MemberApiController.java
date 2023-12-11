package com.todos.mmd.api.member;

import com.todos.mmd.api.member.request.MemberRequest;
import com.todos.mmd.application.member.MemberService;
import com.todos.mmd.auth.application.MemberDetails;
import com.todos.mmd.application.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /* 일반회원 정보 수정 */
    @PutMapping("/{memberNo}")
    public ResponseEntity<Void> updateMember(@PathVariable Long memberNo,
                                             @AuthenticationPrincipal MemberDetails memberDetails,
                                             @RequestBody @Valid MemberRequest.MemberUpdateRequest request
    ) {
        memberService.updateMember(MemberUpdateDto.of(request, memberDetails.getMemberNo(), memberNo));
        return ResponseEntity.ok().build();
    }
}
