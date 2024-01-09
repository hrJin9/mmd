package com.mmd;

import com.mmd.application.MemberDetails;
import com.mmd.member.MemberService;
import com.mmd.member.dto.MemberUpdateDto;
import com.mmd.request.MemberRequest;
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
        memberService.updateMember(MemberUpdateDto.of(
                memberDetails.getMemberNo(),
                memberNo,
                request.getName(),
                request.getPhone(),
                request.getAddress())
        );
        return ResponseEntity.ok().build();
    }
}
