package com.mmd.repository.custom;

import com.mmd.entity.Comment;

import java.util.List;

public interface CustomCommentRepository {
    List<Comment> findByDiaryId(Long loginId, Long diaryId);
}
