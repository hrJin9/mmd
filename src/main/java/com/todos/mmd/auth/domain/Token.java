package com.todos.mmd.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {
    @Id
    private Long memberNo;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_no")
    private Member member;
    private String refreshToken;
    private int reissueCount = 0;

    public Token(Member member, String refreshToken) {
        this.member = member;
        this.refreshToken = refreshToken;
    }

    public static Token from(Member member, String refreshToken) {
        return new Token(
                member,
                refreshToken
        );
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean validateRefreshToken(String refreshToken) {
        return this.refreshToken.equals(refreshToken);
    }

    public void increaseReissueCount() {
        reissueCount++;
    }

}
