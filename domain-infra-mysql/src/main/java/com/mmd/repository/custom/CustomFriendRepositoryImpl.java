package com.mmd.repository.custom;

import com.mmd.domain.FriendStatus;
import com.mmd.entity.QMember;
import com.mmd.vo.FriendFindResultVO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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
                .select(Projections.constructor(FriendFindResultVO.class,
                                                friend.id,
                                                new CaseBuilder()
                                                        .when(friend.requester.id.eq(memberId)).then(friend.respondent)
                                                        .otherwise(friend.requester)))
                .from(friend)
                .where(findByRequesterOrRespondentId(memberId), findByFriendStatus(FriendStatus.Y))
                .fetch();
    }

    @Override
    public List<FriendFindResultVO> findAllFriendRequests(Long respondentId) {
        return queryFactory
                .select(Projections.constructor(FriendFindResultVO.class,
                                                friend.id,
                                                friend.requester))
                .from(friend)
                .join(friend.requester, requester)
                .where(findByRespondentId(respondentId), findByFriendStatus(FriendStatus.IN_PROGRESS))
                .fetch();
    }

    private BooleanExpression findByRequesterId(Long memberId) {
        return Objects.isNull(memberId) ? null : friend.requester.id.eq(memberId);
    }

    private BooleanExpression findByRespondentId(Long memberId) {
        return Objects.isNull(memberId) ? null : friend.respondent.id.eq(memberId);
    }

    private BooleanExpression findByRequesterOrRespondentId(Long memberId) {
        return Objects.isNull(memberId) ? null : friend.requester.id.eq(memberId).or(friend.respondent.id.eq(memberId));
    }

    private BooleanExpression findByFriendStatus(FriendStatus friendStatus) {
        return Objects.isNull(friendStatus) ? null : friend.friendStatus.eq(friendStatus);
    }
}
