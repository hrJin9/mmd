package com.mmd.friend;

import com.mmd.domain.FriendStatus;
import com.mmd.domain.UseStatus;
import com.mmd.entity.Friend;
import com.mmd.entity.Member;
import com.mmd.exception.BadRequestException;
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
import java.util.Optional;
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

    /* 친구 신청대상 검색 */
//    @Transactional(readOnly = true)
//    public List<>

    /* 친구 신청 */
    @Transactional
    public void createFriendRequest(Long requesterId, Long respondentId) {
        Member requester = memberService.findValidMember(requesterId);
        Member respondent = memberService.findValidMember(respondentId);

        Optional<Friend> result = friendRepository.findByRequesterAndRespondent(requester, respondent);
        if(result.isPresent()) { // 친구(신청) 내역이 있는 경우 기존 내역을 가져온다.
            Friend friend = result.get();
            // 이미 친구인 경우
            if(friend.getFriendStatus().equals(FriendStatus.Y) && friend.getUseStatus().equals(UseStatus.IN_USE)) {
                throw new BadRequestException("이미 친구인 회원입니다.");
            }

            friend.createFriendRequest();
        } else { // 친구(신청) 내역이 없는 경우, 새로 생성한다.
            Friend friend = Friend.of(requester, respondent);
            friendRepository.save(friend);
        }
    }

    /* 친구 신청 취소 */
    @Transactional
    public void deleteFriendRequest(Long memberId, Long friendId) {
        Friend friend = findValidFriend(friendId);

        if(!friend.getFriendStatus().equals(FriendStatus.IN_PROGRESS)) {
            throw new ContentsNotFoundException("신청중인 내역이 아닙니다.");
        }

        // 로그인한 사용자의 정보가 requester의 id와 일치하지 않는다면
        if(!friend.getRequester().getId().equals(memberId)) {
            throw new MemberNotValidException("로그인한 사용자가 신청한 내역이 아닙니다.");
        }

        friend.deleteFriend();
    }

    /* 친구 신청 수락/거절 */
    @Transactional
    public void updateFriendRequest(Long memberId, Long friendId, FriendStatus friendStatus) {
        Friend friend = findValidFriend(friendId);

        if(!friend.getFriendStatus().equals(FriendStatus.IN_PROGRESS)) {
            throw new ContentsNotFoundException("신청중인 내역이 아닙니다.");
        }

        // 로그인한 사용자의 정보가 respondent의 id와 일치하지 않는다면
        if(!friend.getRespondent().getId().equals(memberId)) {
            throw new MemberNotValidException("로그인한 사용자에 대한 친구 신청이 아닙니다.");
        }

        friend.updateFriendRequest(friendStatus);
    }

    /* 친구 삭제 */
    @Transactional
    public void deleteFriend(Long memberId, Long friendId) {
        Friend friend = findValidFriend(friendId);

        if(!friend.getFriendStatus().equals(FriendStatus.Y)) {
            throw new ContentsNotFoundException("친구 상태가 아닙니다.");
        }

        if(!(friend.getRequester().getId().equals(memberId) || friend.getRespondent().getId().equals(memberId))) {
            throw new MemberNotValidException("로그인한 사용자의 친구가 아닙니다.");
        }

        friend.deleteFriend();
    }

    /* friend 유효성 검사 후 반환 */
    public Friend findValidFriend(Long friendId) {
        return friendRepository.findByIdAndUseStatus(friendId, UseStatus.IN_USE)
                .orElseThrow(() -> new ContentsNotFoundException("존재하지 않는 내역입니다."));
    }
}
