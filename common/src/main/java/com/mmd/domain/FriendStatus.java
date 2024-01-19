package com.mmd.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendStatus {
    Y("친구"),
    IN_PROGRESS("친구 신청"),
    N("친구 신청 거절");

    private final String dc;
}
