package com.mmd.entity;

import com.mmd.domain.FriendStatus;
import com.mmd.domain.UseStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends CommonDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member requester;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member respondent;

    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus = FriendStatus.IN_PROGRESS;

    @Enumerated(EnumType.STRING)
    private UseStatus useStatus = UseStatus.IN_USE;

    @Builder
    public Friend(Member requester, Member respondent, FriendStatus friendStatus, UseStatus useStatus) {
        this.requester = requester;
        this.respondent = respondent;
        this.friendStatus = friendStatus;
        this.useStatus = useStatus;
    }

    // 친구 신청 수락/거절
    public void updateFriendRequest(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
    }

    // 친구 신청
    public static Friend of(Member requester, Member respondent) {
        return Friend.builder()
                .requester(requester)
                .respondent(respondent).build();
    }

    // 친구 삭제
    public void deleteFriend() {
        this.friendStatus = FriendStatus.N;
        this.useStatus = UseStatus.DELETED;
    }
}

