package com.mmd.repository.custom;

import com.mmd.vo.FriendFindResultVO;

import java.util.List;

public interface CustomFriendRepository {
    List<FriendFindResultVO> findAllFriends(Long memberId);

    List<FriendFindResultVO> findAllFriendRequests(Long respondentId);
}
