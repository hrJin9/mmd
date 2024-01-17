package com.mmd.repository.custom;

import com.mmd.entity.Comment;

import java.util.List;

public interface CustomCommentRepository {
    List<Comment> findAllComment(Long diaryId);
}
