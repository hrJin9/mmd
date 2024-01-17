package com.mmd.friend.response;

import com.mmd.friend.dto.FriendFindResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FriendResponse {

    @Getter
    @AllArgsConstructor
    public static class ViewFriends {
        private Long friendId;
        private Long memberId;
        private String nickName;
        private String name;

        public static ViewFriends from(FriendFindResultDto serviceDto) {
            return new ViewFriends(
                    serviceDto.getFriendId(),
                    serviceDto.getMemberId(),
                    serviceDto.getNickName(),
                    serviceDto.getName()
            );
        }
    }

}
