package com.mmd.repository.custom;

import com.mmd.domain.FriendStatus;
import com.mmd.entity.Friend;

import java.util.List;

public interface CustomFriendRepository {
    List<Friend> findAllFriends(Long memberId, FriendStatus friendStatus);
}
