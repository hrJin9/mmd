package com.todos.mmd.repository.member;

import com.todos.mmd.auth.domain.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
    Optional<MemberRefreshToken> findByMemberNoAndReissueCountLessThan(Long memberNo, long count);

    Optional<MemberRefreshToken> findByMemberNo(Long memberNo);
}
