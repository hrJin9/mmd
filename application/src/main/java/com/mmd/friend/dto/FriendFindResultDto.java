package com.mmd.friend.dto;

import com.mmd.entity.Member;
import com.mmd.vo.FriendFindResultVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendFindResultDto {
    private final Long friendId;
    private final Long memberId;
    private final String nickName;
    private final String name;

    public static FriendFindResultDto of(Long friendId, Member memberFriend) {
        return new FriendFindResultDto(
                friendId,
                memberFriend.getId(),
                memberFriend.getNickName(),
                memberFriend.getName()
        );
    }

    public static FriendFindResultDto from(FriendFindResultVO friendFindResultVO) {
        return new FriendFindResultDto(
                friendFindResultVO.getFriendId(),
                friendFindResultVO.getRequester().getId(),
                friendFindResultVO.getRequester().getNickName(),
                friendFindResultVO.getRequester().getName()
        );
    }
}
