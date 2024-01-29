package com.mmd.repository.custom;

import com.mmd.domain.DiaryVisibility;
import com.mmd.domain.FriendStatus;
import com.mmd.entity.Diary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mmd.entity.QDiary.diary;
import static com.mmd.entity.QFriend.friend;

@Repository
@RequiredArgsConstructor
public class CustomDiaryRepositoryImpl implements CustomDiaryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Diary> findAllDiaries(Long loginId, Pageable pageable) {
        // FRIEND, PUBLIC인 다이어리만 조회한다.
        List<Diary> diaries = queryFactory
                .selectFrom(diary)
                .leftJoin(friend).on(friend.friendStatus.eq(FriendStatus.Y).and(friendCondition(loginId)))
                .where(findByDiaryVisibility(DiaryVisibility.PUBLIC)
                        .or(findByDiaryVisibility(DiaryVisibility.FRIEND)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = getCount(loginId);
        return PageableExecutionUtils.getPage(diaries, pageable, countQuery::fetchOne);
    }

    private JPAQuery<Long> getCount(Long loginId) {
        return queryFactory
                .select(diary.count())
                .from(diary)
                .leftJoin(friend).on(friendCondition(loginId))
                .where(findByDiaryVisibility(DiaryVisibility.PUBLIC)
                        .or(findByDiaryVisibility(DiaryVisibility.FRIEND)));
    }
    
    // friend join 조건
    private BooleanExpression friendCondition(Long loginId) {
        return loginId == null ? null : findFriendByRequester(loginId).or(findFriendByRespondent(loginId));
    }
    
    // diary visibility 조인 조건
    private BooleanExpression findByDiaryVisibility(DiaryVisibility diaryVisibility) {
        BooleanExpression diaryBoolean = diary.diaryVisibility.eq(diaryVisibility);
        return diaryVisibility.equals(DiaryVisibility.FRIEND) ? diaryBoolean.and(friend.requester.id.isNotNull()) : diaryBoolean;
    }

    //
    private BooleanExpression findFriendByRequester(Long loginId) {
        return loginId == null ? null : diary.writer.id.eq(friend.requester.id).and(friend.respondent.id.eq(loginId));
    }

    private BooleanExpression findFriendByRespondent(Long loginId) {
        return loginId == null ? null : diary.writer.id.eq(friend.respondent.id).and(friend.requester.id.eq(loginId));
    }

    private BooleanExpression findFriendCondition(FriendStatus friendStatus) {
        return friendStatus == null ? null : friend.friendStatus.eq(friendStatus);
    }

}
