package com.mmd.repository.custom;

import com.mmd.domain.DiaryVisibility;
import com.mmd.domain.FriendStatus;
import com.mmd.entity.Diary;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

import static com.mmd.entity.QDiary.diary;
import static com.mmd.entity.QFriend.friend;

@Repository
@RequiredArgsConstructor
public class CustomDiaryRepositoryImpl implements CustomDiaryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Diary> findAllDiaries(Long loginId, Pageable pageable) {
        // FRIEND, PUBLIC인 다이어리만 조회한다.
        // FRIEND인 경우, 로그인한 멤버와 작성한 멤버가 친구관계일 때만 조회한다.
        // TODO : 이게 도메인 비즈니스인걸까?? 서비스 비즈니스 아닐까?
        List<Diary> diaries = queryFactory
                .selectFrom(diary)
                .leftJoin(diary.writer, friend.requester).fetchJoin()
                .leftJoin(diary.writer, friend.respondent).fetchJoin()
                .where(findCondition(loginId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = getCount(loginId);

        return PageableExecutionUtils.getPage(diaries, pageable, () -> countQuery.fetchOne());
    }

    private JPAQuery<Long> getCount(Long loginId) {
        return queryFactory
                .select(diary.count())
                .from(diary)
                .where(findCondition(loginId));
    }

    private BooleanBuilder findCondition(Long loginId) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // PUBLIC
        booleanBuilder.or(diary.diaryVisibility.eq(DiaryVisibility.PUBLIC));

        // FRIEND
        booleanBuilder.or(
                diary.diaryVisibility.eq(DiaryVisibility.FRIEND)
                .and(friend.friendStatus.eq(FriendStatus.Y))
        );

        // PRIVATE
        booleanBuilder.or(diary.diaryVisibility.eq(DiaryVisibility.PRIVATE)
                .and(diary.writer.id.eq(loginId)));
        return booleanBuilder;
    }



}
