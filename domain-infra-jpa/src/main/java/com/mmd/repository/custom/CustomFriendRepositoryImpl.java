package com.mmd.repository.custom;

import com.mmd.domain.FriendStatus;
import com.mmd.entity.Friend;
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
    public List<Friend> findAllFriends(Long memberId, FriendStatus friendStatus) {
        return queryFactory
                .selectFrom(friend)
                .where(findByMemberId(memberId), findByFriendStatus(friendStatus))
                .fetch();
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
