package com.mmd.friend;

import com.mmd.friend.dto.FriendFindResultDto;
import com.mmd.friend.request.FriendRequest;
import com.mmd.friend.response.FriendResponse;
import com.mmd.security.MemberDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "친구 API")
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @ApiOperation(value = "친구 목록 조회", notes = "로그인한 회원의 친구 목록을 조회합니다.", tags = "친구 API")
    @GetMapping
    public ResponseEntity<List<FriendResponse.ViewFriends>> findFriends(@AuthenticationPrincipal MemberDetails memberDetails) {
        List<FriendFindResultDto> friends = friendService.findFriends(memberDetails.getId());
        List<FriendResponse.ViewFriends> friendsResponse = friends.stream()
                .map(FriendResponse.ViewFriends::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(friendsResponse);
    }
    
    @ApiOperation(value = "친구 신청 목록 조회", notes = "로그인한 회원의 친구 신청 목록을 조회합니다.", tags = "친구 API")
    @GetMapping("/requests")
    public ResponseEntity<List<FriendResponse.ViewFriends>> findFriendRequests(@AuthenticationPrincipal MemberDetails memberDetails) {
        List<FriendFindResultDto> friends = friendService.findFriendRequests(memberDetails.getId());
        List<FriendResponse.ViewFriends> friendsResponse = friends.stream()
                .map(FriendResponse.ViewFriends::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(friendsResponse);
    }

    @ApiOperation(value = "친구 신청 수락/거절", notes = "로그인한 회원의 친구 신청 중 하나를 수락하거나 거절합니다.", tags = "친구 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "friendId", value = "친구 번호", example = "1", dataType = "Long", paramType = "path")
    })
    @PutMapping("/requests/{friendId}")
    public ResponseEntity<Void> updateFriendRequest(@AuthenticationPrincipal MemberDetails memberDetails,
                                                    @PathVariable Long friendId,
                                                    @RequestBody @Valid FriendRequest.UpdateFriendRequest request) {
        friendService.updateFriendRequest(memberDetails.getId(), friendId, request.getFriendStatus());
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "친구 신청 취소", notes = "특정 회원에게 요청한 친구 신청을 취소합니다.", tags = "친구 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "friendId", value = "친구 번호", example = "1", dataType = "Long", paramType = "path")
    })
    @DeleteMapping("/requests/{friendId}")
    public ResponseEntity<Void> deleteFriendRequest(@AuthenticationPrincipal MemberDetails memberDetails,
                                                    @PathVariable Long friendId) {
        friendService.deleteFriendRequest(memberDetails.getId(), friendId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "친구 신청", notes = "특정 회원에게 친구 신청을 합니다.", tags = "친구 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "유저 번호", example = "1", dataType = "Long", paramType = "path")
    })
    @PostMapping("/{memberId}")
    public ResponseEntity<Void> createFriendRequest(@AuthenticationPrincipal MemberDetails memberDetails,
                                                    @PathVariable Long memberId) {
        friendService.createFriendRequest(memberDetails.getId(), memberId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "친구 삭제", notes = "특정 회원의 친구 관계를 삭제 처리합니다.", tags = "친구 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "friendId", value = "친구 번호", example = "1", dataType = "Long", paramType = "path")
    })
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@AuthenticationPrincipal MemberDetails memberDetails,
                                             @PathVariable Long friendId) {
        friendService.deleteFriend(memberDetails.getId(), friendId);
        return ResponseEntity.noContent().build();
    }
}
