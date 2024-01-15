package com.mmd.repository.custom;

import com.mmd.entity.Comment;

import java.util.Optional;

public interface CustomCommentRepository {
    Optional<Comment> findAllComment(Long diaryno, Long memberNo, Integer pageSize);
}
