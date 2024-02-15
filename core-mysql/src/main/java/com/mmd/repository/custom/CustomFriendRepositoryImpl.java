package com.mmd.repository.custom;

import com.mmd.domain.FriendStatus;
import com.mmd.entity.Friend;
import com.mmd.entity.QMember;
import com.mmd.vo.FriendFindResultVO;
import com.mmd.vo.QFriendFindResultVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mmd.entity.QFriend.friend;

@Repository
@RequiredArgsConstructor
public class CustomFriendRepositoryImpl implements CustomFriendRepository {
    private static final QMember requester = new QMember("requester");
    private static final QMember respondent = new QMember("respondent");
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FriendFindResultVO> findAllFriends(Long memberId) {
        return queryFactory
                .select(new QFriendFindResultVO(
                        friend.id,
                        new CaseBuilder()
                                .when(friend.requester.id.eq(memberId)).then(friend.respondent)
                                .otherwise(friend.requester)
                ))
                .from(friend)
                .where(requesterIdEq(memberId).or(respondentIdEq(memberId))
                      ,friendStatusEq(FriendStatus.Y))
                .fetch();
    }

    @Override
    public List<FriendFindResultVO> findAllFriendRequests(Long respondentId) {
        return queryFactory
                .select(new QFriendFindResultVO(
                        friend.id,
                        friend.requester
                ))
                .from(friend)
                .join(friend.requester, requester)
                .where(respondentIdEq(respondentId)
                      ,friendStatusEq(FriendStatus.IN_PROGRESS))
                .fetch();
    }

    @Override
    public Optional<Friend> existsFriend(Long loginId, Long memberId) {
        Friend result = queryFactory
                        .selectFrom(friend)
                        .where(requesterIdEq(loginId).and(respondentIdEq(memberId))
                                .or(requesterIdEq(memberId).and(respondentIdEq(loginId))),
                                friendStatusEq(FriendStatus.Y))
                        .fetchOne();
        return Optional.ofNullable(result);

    }

    private BooleanExpression requesterIdEq(Long memberId) {
        return memberId != null ? friend.requester.id.eq(memberId) : null;
    }

    private BooleanExpression respondentIdEq(Long memberId) {
        return memberId != null ? friend.respondent.id.eq(memberId) : null;
    }

    private BooleanExpression friendStatusEq(FriendStatus friendStatus) {
        return friendStatus != null ? friend.friendStatus.eq(friendStatus) : null;
    }
}
