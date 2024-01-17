package com.mmd.repository;

import com.mmd.entity.Friend;
import com.mmd.repository.custom.CustomFriendRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long>, CustomFriendRepository {
}
