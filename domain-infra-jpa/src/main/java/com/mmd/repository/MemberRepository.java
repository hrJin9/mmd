package com.mmd.repository;
import com.mmd.domain.UseStatus;
import com.mmd.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdAndUseStatus(Long id, UseStatus useStatus);

    Optional<Member> findByEmail(String email);
}
