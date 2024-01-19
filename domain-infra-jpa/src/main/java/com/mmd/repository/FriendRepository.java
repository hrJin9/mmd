package com.mmd.repository;

import com.mmd.domain.FriendStatus;
import com.mmd.domain.UseStatus;
import com.mmd.entity.Friend;
import com.mmd.entity.Member;
import com.mmd.repository.custom.CustomFriendRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long>, CustomFriendRepository {
    Optional<Friend> findByIdAndFriendStatusAndUseStatus(Long friendId, FriendStatus friendStatus, UseStatus useStatus);

    Optional<Friend> findByIdAndUseStatus(Long friendId, UseStatus useStatus);

    Optional<Friend> findByRequesterAndRespondent(Member Requester, Member Respondent);
}
