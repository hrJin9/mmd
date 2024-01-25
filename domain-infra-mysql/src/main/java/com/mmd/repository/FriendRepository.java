package com.mmd.repository;

import com.mmd.entity.Friend;
import com.mmd.entity.Member;
import com.mmd.repository.custom.CustomFriendRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long>, CustomFriendRepository {
//    @EntityGraph(attributePaths = {"requester", "respondent"}) // TODO : fetch join을 하는게 좋을지, 그냥 2번 쿼리를 보내는 게 좋을지?
//    Optional<Friend> findByIdAndUseStatus(Long friendId, UseStatus useStatus);
    Optional<Friend> findByRequesterAndRespondent(Member Requester, Member Respondent);
}
