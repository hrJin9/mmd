package com.mmd.vo;

import com.mmd.entity.Member;
import lombok.Getter;

@Getter
public class FriendFindResultVO {
    private final Long friendId;
    private final Member requester;

    private FriendFindResultVO(Long friendId, Member requester) {
        this.friendId = friendId;
        this.requester = requester;
    }
}
