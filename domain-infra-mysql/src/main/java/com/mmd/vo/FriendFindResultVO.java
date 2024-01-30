package com.mmd.vo;

import com.mmd.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class FriendFindResultVO {
    private final Long friendId;
    private final Member friend;

    @QueryProjection
    public FriendFindResultVO(Long friendId, Member friend) {
        this.friendId = friendId;
        this.friend = friend;
    }

}
