package com.mmd.friend.dto;

import com.mmd.entity.Friend;
import com.mmd.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendFindResultDto {
    private final Long friendId;
    private final Long memberId;
    private final String nickName;
    private final String name;

    public static FriendFindResultDto of(Long loginMemberId, Friend friend) {
        // 로그인한 회원이 requester라면 respondent를, 아니라면 반대 상대에 대한 정보를 가져온다.
        Member respondent = (friend.getRequester().getId().equals(loginMemberId)) ? friend.getRespondent() : friend.getRequester();
        return new FriendFindResultDto(
                friend.getId(),
                respondent.getId(),
                respondent.getNickName(),
                respondent.getName()
        );
    }
}
