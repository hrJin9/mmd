package com.mmd.vo;

import com.mmd.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendFindResultVO {
    private final Long friendId;
    private final Member friend;
}
