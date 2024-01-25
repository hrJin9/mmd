package com.mmd.entity;

import com.mmd.domain.FriendStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@OnDelete(action = OnDeleteAction.CASCADE) // hard delete 방지
@SQLDelete(sql = "UPDATE friend SET deleted_date = CURRENT_TIMESTAMP WHERE friend_id = ?") // soft delete
@Where(clause = "deleted_date is null") // delete 되지 않은것만 조회
public class Friend extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "member_id", name = "requester_id")
    private Member requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "member_id", name = "respondent_id")
    private Member respondent;

    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;

    @Builder
    public Friend(Member requester, Member respondent, FriendStatus friendStatus) {
        this.requester = requester;
        this.respondent = respondent;
        this.friendStatus = friendStatus;
    }

    // 친구 신청 수락/거절
    public void updateFriendRequest(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
    }

    // 친구 신청
    public void createFriendRequest() {
        this.friendStatus = FriendStatus.IN_PROGRESS;
    }

    // 친구 신청시 entity 생성
    public static Friend of(Member requester, Member respondent) {
        return Friend.builder()
                .requester(requester)
                .respondent(respondent)
                .build();
    }
}

