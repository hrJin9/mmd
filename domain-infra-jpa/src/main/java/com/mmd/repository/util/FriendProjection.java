package com.mmd.repository.util;

import com.mmd.entity.QFriend;
import com.mmd.entity.QMember;
import com.mmd.vo.SimpleFriendVO;
import com.mmd.vo.SimpleMemberVO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;

public class FriendProjection {
    public static QBean<?> simpleFriend(QFriend friend) {
        return Projections.bean(SimpleFriendVO.class,
                friend.id);
    }
}
