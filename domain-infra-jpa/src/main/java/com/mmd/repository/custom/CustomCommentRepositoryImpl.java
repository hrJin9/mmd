package com.mmd.repository.custom;

import com.mmd.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Comment> findAllComment(Long diaryno, Long memberNo, Integer pageSize) {
        return Optional.empty();
    }
}
