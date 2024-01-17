package com.mmd.friend.request;

import com.mmd.domain.FriendStatus;
import lombok.Getter;

public class FriendRequest {
    @Getter
    public static class UpdateFriendRequest {
        private FriendStatus friendStatus;
    }

}
