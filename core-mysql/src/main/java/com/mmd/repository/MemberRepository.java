package com.mmd.repository;
import com.mmd.domain.OAuthProvider;
import com.mmd.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findBySocialTypeAndSocialId(OAuthProvider OAuthProvider, String socialId);
}
