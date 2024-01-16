package com.mmd.repository;

import com.mmd.entity.Comment;
import com.mmd.repository.custom.CustomCommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {
}
