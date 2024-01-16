package com.mmd.repository.custom;

import com.mmd.entity.Comment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.mmd.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findAllComment(Long diaryno) {
        return queryFactory
                .selectFrom(comment)
                .where(findByDiaryNo(diaryno))
//                .offset(pageable.getOffset())   // 페이지 번호
//                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    private BooleanExpression findByDiaryNo(Long diaryNo) {
        return Objects.isNull(diaryNo) ? null : comment.diary.diaryNo.eq(diaryNo);
    }

    private BooleanExpression findByMemberNo(Long memberNo) {
        return Objects.isNull(memberNo) ? null : comment.diary.diaryNo.eq(memberNo);
    }
}
