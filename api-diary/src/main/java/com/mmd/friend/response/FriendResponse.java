package com.mmd.friend.response;

import com.mmd.friend.dto.FriendFindResultDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FriendResponse {

    @Getter
    @AllArgsConstructor
    @ApiModel(value = "친구 response data")
    public static class ViewFriends {
        @ApiModelProperty(value = "친구 번호", example = "1")
        private Long friendId;
        @ApiModelProperty(value = "유저 번호", example = "1")
        private Long memberId;
        @ApiModelProperty(value = "유저 닉네임", example = "달래")
        private String nickName;
        @ApiModelProperty(value = "유저 이름", example = "진혜린")
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
