package com.mmd.repository.custom;

import com.mmd.entity.Friend;
import com.mmd.vo.FriendFindResultVO;
import com.mmd.vo.SimpleFriendVO;

import java.util.List;

public interface CustomFriendRepository {
    List<Friend> findAllFriends(Long memberId);

    List<FriendFindResultVO> findAllFriendRequests(Long respondentId);
}
