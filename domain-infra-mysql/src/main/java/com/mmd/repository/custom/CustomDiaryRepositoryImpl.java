package com.mmd.repository.custom;

import com.mmd.domain.DiaryVisibility;
import com.mmd.domain.FriendStatus;
import com.mmd.entity.Diary;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Diary> findOthersDiaries(Long loginId, Pageable pageable) {
        List<Diary> diaries = queryFactory
                .selectFrom(diary)
                .leftJoin(friend)
                .on(friendStatusEq(FriendStatus.Y),
                    (respondentIdEq(loginId).or(requesterIdEq(loginId)))
                )
                .where(diaryVisibilityEq(DiaryVisibility.PUBLIC).or(diaryVisibilityEq(DiaryVisibility.FRIEND)),
                        friend.requester.id.isNotNull()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(diarySort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = getOthersCount(loginId);
        return PageableExecutionUtils.getPage(diaries, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<Diary> findOtherDiaries(Long loginId, Long memberId, Pageable pageable) {
        // FRIEND, PUBLIC인 다이어리를 조회한다
        List<Diary> diaries = queryFactory
                .selectFrom(diary)
                .leftJoin(friend)
                .on(friendStatusEq(FriendStatus.Y),
                        (respondentIdEq(loginId).or(requesterIdEq(loginId)))
                )
                .where(writerIdEq(memberId),
                       diaryVisibilityEq(DiaryVisibility.PUBLIC).or(diaryVisibilityEq(DiaryVisibility.FRIEND)),
                       friend.requester.id.isNotNull()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(diarySort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = getOthersCount(loginId);
        return PageableExecutionUtils.getPage(diaries, pageable, countQuery::fetchOne);
    }
//
//    @Override
//    public Optional<Diary> findOtherOneDiary(Long loginId, Long memberId, Long diaryId) {
//        Diary result = queryFactory
//                .selectFrom(diary)
//                .on(friendStatusEq(FriendStatus.Y),
//                    (respondentIdEq(loginId).or(requesterIdEq(loginId)))
//                )
//                .where(diaryIdEq(diaryId),
//                        writerIdEq(memberId),
//                        diaryVisibilityEq(DiaryVisibility.PUBLIC).or(diaryVisibilityEq(DiaryVisibility.FRIEND)),
//                        friend.requester.id.isNotNull()
//                )
//                .fetchOne();
//        return Optional.ofNullable(result);
//    }

    private JPAQuery<Long> getOthersCount(Long loginId) {
        return queryFactory
                .select(diary.count())
                .from(diary)
                .leftJoin(friend)
                .on(friendStatusEq(FriendStatus.Y),
                        (respondentIdEq(loginId).or(requesterIdEq(loginId)))
                )
                .where(diaryVisibilityEq(DiaryVisibility.PUBLIC).or(diaryVisibilityEq(DiaryVisibility.FRIEND)),
                        friend.requester.id.isNotNull()
                );
    }

    private BooleanExpression diaryIdEq(Long diaryId) {
        return diaryId != null ? diary.id.eq(diaryId) : null;
    }

    private BooleanExpression writerIdEq(Long writerId) {
        return writerId != null ? diary.writer.id.eq(writerId) : null;
    }

    private BooleanExpression diaryVisibilityEq(DiaryVisibility diaryVisibility) {
        return diaryVisibility != null ? diary.diaryVisibility.eq(diaryVisibility) : null;
    }

    private BooleanExpression respondentIdEq(Long loginId) {
        return loginId != null ? diary.writer.id.eq(friend.requester.id).and(friend.respondent.id.eq(loginId)) : null;
    }

    private BooleanExpression requesterIdEq(Long loginId) {
        return loginId != null ? diary.writer.id.eq(friend.respondent.id).and(friend.requester.id.eq(loginId)) : null;
    }

    private BooleanExpression friendStatusEq(FriendStatus friendStatus) {
        return friendStatus != null ? friend.friendStatus.eq(friendStatus) : null;
    }

    // 정렬
    // TODO : 하드코딩 수정..
    private OrderSpecifier<?> diarySort(Pageable pageable) {
        for(Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            switch (order.getProperty()) {
                case "CREATED_DATE" :
                    return new OrderSpecifier<>(direction, diary.createdDate);
                case "LAST_MODIFIED_DATE" :
                    return new OrderSpecifier<>(direction, diary.lastModifiedDate);
                case "DELETED_DATE" :
                    return new OrderSpecifier<>(direction, diary.deletedDate);
            }
        }
        return null;
    }

}
