package com.mmd.comment.response;

import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.comment.request.CommentRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommentResponse {
    @ApiModel(description = "댓글 response data")
    @Getter
    @AllArgsConstructor
    public static class ViewComments {
        @ApiModelProperty(example = "나도 여기 가보고싶어!")
        private String contents;

        @ApiModelProperty(example = "1")
        private Long writerId;

        @ApiModelProperty(example = "달래")
        private String writerNickName;

        public static CommentResponse.ViewComments from(CommentFindResultDto serviceDto) {
            return new CommentResponse.ViewComments(
                    serviceDto.getContents(),
                    serviceDto.getWriterId(),
                    serviceDto.getWriterNickName()
            );
        }
    }

}
