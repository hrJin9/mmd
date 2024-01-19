package com.mmd.repository.custom;

import com.mmd.domain.FriendStatus;
import com.mmd.domain.UseStatus;
import com.mmd.entity.Friend;
import com.mmd.vo.FriendFindResultVO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.mmd.entity.QFriend.friend;

@Repository
@RequiredArgsConstructor
public class CustomFriendRepositoryImpl implements CustomFriendRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Friend> findAllFriends(Long memberId) {
        return queryFactory
                .selectFrom(friend)
                .where(findByMemberId(memberId), findByFriendStatus(FriendStatus.Y), findByUseStatus(UseStatus.IN_USE))
                .fetch();
    }

    @Override
    public List<FriendFindResultVO> findAllFriendRequests(Long respondentId) {
        return queryFactory
                .select(Projections.bean(FriendFindResultVO.class,
                        friend.id,
                        friend.requester))
                .from(friend)
                .where(findByRespondentId(respondentId), findByFriendStatus(FriendStatus.IN_PROGRESS), findByUseStatus(UseStatus.IN_USE))
                .fetch();
    }

    private BooleanExpression findByUseStatus(UseStatus useStatus) {
        return Objects.isNull(useStatus) ? null : friend.useStatus.eq(useStatus);
    }

    private BooleanExpression findByRequesterId(Long memberId) {
        return Objects.isNull(memberId) ? null : friend.requester.id.eq(memberId);
    }

    private BooleanExpression findByRespondentId(Long memberId) {
        return Objects.isNull(memberId) ? null : friend.respondent.id.eq(memberId);
    }

    private BooleanExpression findByMemberId(Long memberId) {
        return Objects.isNull(memberId) ? null : friend.requester.id.eq(memberId).or(friend.respondent.id.eq(memberId));
    }

    private BooleanExpression findByFriendStatus(FriendStatus friendStatus) {
        return Objects.isNull(friendStatus) ? null : friend.friendStatus.eq(friendStatus);
    }
}
