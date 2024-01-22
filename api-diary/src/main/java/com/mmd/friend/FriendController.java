package com.mmd.friend;

import com.mmd.friend.dto.FriendFindResultDto;
import com.mmd.friend.request.FriendRequest;
import com.mmd.friend.response.FriendResponse;
import com.mmd.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    /* 친구 조회 */
    @GetMapping
    public ResponseEntity<List<FriendResponse.ViewFriends>> findFriends(@AuthenticationPrincipal MemberDetails memberDetails) {
        List<FriendFindResultDto> friends = friendService.findFriends(memberDetails.getId());
        List<FriendResponse.ViewFriends> friendsResponse = friends.stream()
                .map(FriendResponse.ViewFriends::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(friendsResponse);
    }
    
    /* 친구요청 확인 */
    @GetMapping("/requests")
    public ResponseEntity<List<FriendResponse.ViewFriends>> findFriendRequests(@AuthenticationPrincipal MemberDetails memberDetails) {
        List<FriendFindResultDto> friends = friendService.findFriendRequests(memberDetails.getId());
        List<FriendResponse.ViewFriends> friendsResponse = friends.stream()
                .map(FriendResponse.ViewFriends::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(friendsResponse);
    }

    /* 친구 요청 수락/거절 */
    @PutMapping("/requests/{friendId}")
    public ResponseEntity<Void> updateFriendRequest(@AuthenticationPrincipal MemberDetails memberDetails,
                                                    @PathVariable Long friendId,
                                                    @RequestBody @Valid FriendRequest.UpdateFriendRequest request) {
        friendService.updateFriendRequest(memberDetails.getId(), friendId, request.getFriendStatus());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/requests/{friendId}")
    public ResponseEntity<Void> deleteFriendRequest(@AuthenticationPrincipal MemberDetails memberDetails,
                                                    @PathVariable Long friendId) {
        friendService.deleteFriendRequest(memberDetails.getId(), friendId);
        return ResponseEntity.noContent().build();
    }

    /* 친구 요청 */
    @PostMapping("/{memberId}")
    public ResponseEntity<Void> createFriendRequest(@AuthenticationPrincipal MemberDetails memberDetails,
                                                    @PathVariable Long memberId) {
        friendService.createFriendRequest(memberDetails.getId(), memberId);
        return ResponseEntity.noContent().build();
    }

    /* 친구 삭제 */
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@AuthenticationPrincipal MemberDetails memberDetails,
                                             @PathVariable Long friendId) {
        friendService.deleteFriend(memberDetails.getId(), friendId);
        return ResponseEntity.noContent().build();
    }
}
