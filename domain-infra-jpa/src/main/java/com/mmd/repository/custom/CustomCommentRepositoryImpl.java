package com.mmd.repository.custom;

import com.mmd.entity.Comment;

import java.util.Optional;

public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    @Override
    public Optional<Comment> findAllComment(Long diaryno, Long memberNo) {
        return Optional.empty();
    }
}
