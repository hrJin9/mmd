package com.mmd.repository.custom;

import com.mmd.domain.CommentVisibility;
import com.mmd.domain.FriendStatus;
import com.mmd.entity.Comment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mmd.entity.QComment.comment;
import static com.mmd.entity.QFriend.friend;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findByDiaryId(Long loginId, Long diaryId) {
        return queryFactory
                .selectFrom(comment)
                .leftJoin(friend)
                .on(friendStatusEq(FriendStatus.Y),
                        (respondentIdEq(loginId).or(requesterIdEq(loginId)))
                )
                .where(diaryIdEq(diaryId),
                        commentVisibilityEq(CommentVisibility.PUBLIC)
                        .or(commentVisibilityEq(CommentVisibility.FRIEND).and(friend.requester.id.isNotNull()))
                )
                .fetch();
    }

    private BooleanExpression friendStatusEq(FriendStatus friendStatus) {
        return friendStatus != null ? friend.friendStatus.eq(friendStatus) : null;
    }

    private BooleanExpression respondentIdEq(Long loginId) {
        return loginId != null ? comment.writer.id.eq(friend.requester.id).and(friend.respondent.id.eq(loginId)) : null;
    }

    private BooleanExpression requesterIdEq(Long loginId) {
        return loginId != null ? comment.writer.id.eq(friend.respondent.id).and(friend.requester.id.eq(loginId)) : null;
    }

    private BooleanExpression diaryIdEq(Long diaryId) {
        return diaryId != null ? comment.diary.id.eq(diaryId) : null;
    }

    private BooleanExpression commentVisibilityEq(CommentVisibility commentVisibility) {
        return commentVisibility != null ? comment.commentVisibility.eq(commentVisibility) : null;
    }
}
