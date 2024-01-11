package com.mmd.member;

import com.mmd.member.dto.MemberUpdateDto;
import com.mmd.member.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    /* 일반회원 정보 수정 */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable String id,
                                             @RequestBody @Valid MemberRequest.MemberUpdateRequest request
    ) {
        memberService.updateMember(MemberUpdateDto.of(
                id,
                request.getName(),
                request.getPhone(),
                request.getAddress())
        );
        return ResponseEntity.ok().build();
    }
}
