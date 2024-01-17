package com.mmd.friend;

import com.mmd.domain.FriendStatus;
import com.mmd.entity.Friend;
import com.mmd.exception.ContentsNotFoundException;
import com.mmd.exception.MemberNotValidException;
import com.mmd.friend.dto.FriendFindResultDto;
import com.mmd.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    
    /* 친구 조회 */
    @Transactional(readOnly = true)
    public List<FriendFindResultDto> findFriends(Long memberId) {
        return findFriendsByStatus(memberId, FriendStatus.Y);
    }
    
    /* 친구 신청 조회 */
    @Transactional(readOnly = true)
    public List<FriendFindResultDto> findFriendRequests(Long memberId) {
        return findFriendsByStatus(memberId, FriendStatus.IN_PROGRESS);
    }
    
    /* 친구 신청 수락/거절 */
    public void updateFriendRequest(Long memberId, Long friendId, FriendStatus friendStatus) {
        Friend friend = findFriendByMemberIdAndFriendId(memberId, friendId);
        friend.updateFriendRequest(friendStatus);
    }

    /* 친구 삭제 */
    public void deleteFriend(Long memberId, Long friendId) {
        Friend friend = findFriendByMemberIdAndFriendId(memberId, friendId);
        friend.deleteFriend();
    }

    // FRIEND STATUS에 따른 friend 조회
    private List<FriendFindResultDto> findFriendsByStatus(Long memberId, FriendStatus friendStatus) {
        List<Friend> friends = friendRepository.findAllFriends(memberId, friendStatus);
        return friends.stream()
                .map(friend -> FriendFindResultDto.of(memberId, friend))
                .collect(Collectors.toList());
    }
    
    // friend 조회
    private Friend findFriendByMemberIdAndFriendId(Long memberId, Long friendId) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new ContentsNotFoundException("존재하지 않는 친구 신청입니다."));
        // 로그인한 사용자의 정보가 requester나 respondent의 id와 일치하지 않는다면
        if(!(friend.getRequester().getId().equals(memberId) || friend.getRespondent().getId().equals(memberId))) {
            throw new MemberNotValidException("로그인한 사용자의 친구 신청이 아닙니다.");
        }

        return friend;
    }
}
