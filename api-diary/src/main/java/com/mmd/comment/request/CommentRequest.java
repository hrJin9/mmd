package com.mmd.comment.request;

import com.mmd.domain.CommentVisibility;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentRequest {
    @ApiModel(description = "댓글 request parameter")
    @Getter
    public static class CreateComment {
        @ApiModelProperty(value = "루트 댓글 번호", example = "1")
        private Long groupId;

        @ApiModelProperty(value = "댓글 레벨", required = true, example = "0")
        @NotNull
        private Long level;

        @ApiModelProperty(value = "상위 코멘트 번호", example = "1")
        private Long upperId;

        @ApiModelProperty(value = "댓글 내용", required = true, example = "나도 오늘 이거 했어!")
        @NotBlank
        private String content;

        @ApiModelProperty(value = "댓글 접근 권한", required = true, example = "FRIEND")
        @NotNull
        private CommentVisibility commentVisibility;
    }

    @Getter
    public static class UpdateComment {
        @ApiModelProperty(value = "댓글 내용", required = true, example = "사실 나 이거 안했어!")
        @NotBlank
        private String content;

        @ApiModelProperty(value = "댓글 접근 권한", example = "PUBLIC")
        private CommentVisibility commentVisibility;
    }
}
