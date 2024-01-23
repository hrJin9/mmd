package com.mmd.repository.custom;

import com.mmd.domain.CommentVisibility;
import com.mmd.domain.DiaryVisibility;
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
    public List<Comment> findAllComment(Long diaryId) {
        return queryFactory
                .selectFrom(comment)
                .where(findByDiaryNo(diaryId), findByVisibility())
//                .offset(pageable.getOffset())   // 페이지 번호
//                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    private BooleanExpression findByDiaryNo(Long diaryId) {
        return Objects.isNull(diaryId) ? null : comment.diary.id.eq(diaryId);
    }

    private BooleanExpression findByMemberNo(Long memberId) {
        return Objects.isNull(memberId) ? null : comment.diary.id.eq(memberId);
    }
    
    // FRIEND, PUBLIC만 조회 가능
    private BooleanExpression findByVisibility() {
        return comment.commentVisibility.eq(CommentVisibility.FRIEND).and(comment.commentVisibility.eq(CommentVisibility.PUBLIC));
    }
}
