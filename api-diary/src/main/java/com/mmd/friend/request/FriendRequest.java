package com.mmd.friend.request;

import com.mmd.domain.FriendStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class FriendRequest {

    @Getter
    @ApiModel(description = "친구 신청 수락/거절 요청 Parameter")
    public static class UpdateFriendRequest {
        @ApiModelProperty(value = "수락/거절할 친구 상태", required = true, example = "Y")
        @NotBlank
        private FriendStatus friendStatus;
    }

}
