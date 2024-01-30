package com.mmd.repository.custom;

import com.mmd.entity.Friend;
import com.mmd.vo.FriendFindResultVO;

import java.util.List;
import java.util.Optional;

public interface CustomFriendRepository {
    List<FriendFindResultVO> findAllFriends(Long memberId);

    List<FriendFindResultVO> findAllFriendRequests(Long respondentId);

    Optional<Friend> existsFriend(Long loginId, Long memberId);
}
