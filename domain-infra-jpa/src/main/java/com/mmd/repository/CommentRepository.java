package com.mmd.repository;

import com.mmd.domain.UseStatus;
import com.mmd.entity.Comment;
import com.mmd.repository.custom.CustomCommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {
    Optional<Comment> findByIdAndUseStatus(Long commentId, UseStatus useStatus);
    List<Comment> findAllByDiaryId(Long diaryId);
}
