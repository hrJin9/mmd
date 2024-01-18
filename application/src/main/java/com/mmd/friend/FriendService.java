package com.mmd.friend;

import com.mmd.domain.FriendStatus;
import com.mmd.entity.Friend;
import com.mmd.entity.Member;
import com.mmd.exception.ContentsNotFoundException;
import com.mmd.exception.MemberNotValidException;
import com.mmd.friend.dto.FriendFindResultDto;
import com.mmd.member.MemberService;
import com.mmd.repository.FriendRepository;
import com.mmd.vo.FriendFindResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberService memberService;

    /* 친구 조회 */
    @Transactional(readOnly = true)
    public List<FriendFindResultDto> findFriends(Long memberId) {
        List<Friend> friends = friendRepository.findAllFriends(memberId);
        return friends.stream()
                .map(friend -> {
                    Member memberFriend = (friend.getRequester().getId().equals(memberId)) ? friend.getRespondent() : friend.getRequester();
                    return FriendFindResultDto.of(friend.getId(), memberFriend);
                })
                .collect(Collectors.toList());
    }
    
    /* 친구 신청 조회 */
    @Transactional(readOnly = true)
    public List<FriendFindResultDto> findFriendRequests(Long respondentId) {
        List<FriendFindResultVO> results = friendRepository.findAllFriendRequests(respondentId);
        return results.stream()
                .map(FriendFindResultDto::from)
                .collect(Collectors.toList());
    }
    
    /* 친구 신청 수락/거절 */
    public void updateFriendRequest(Long memberId, Long friendId, FriendStatus friendStatus) {
        Friend friend = findFriendByMemberIdAndFriendId(memberId, friendId);
        friend.updateFriendRequest(friendStatus);
    }

    /* 친구 신청 */
    public void createFriendRequest(Long requesterId, Long respondentId) {
        Member requester = memberService.findMember(requesterId);
        Member respondent = memberService.findMember(respondentId);

        Friend friend = Friend.of(requester, respondent);
        friendRepository.save(friend);
    }

    /* 친구 삭제 */
    public void deleteFriend(Long memberId, Long friendId) {
        Friend friend = findFriendByMemberIdAndFriendId(memberId, friendId);
        friend.deleteFriend();
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
