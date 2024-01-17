package com.mmd.comment.request;

import com.mmd.domain.CommentVisibility;
import com.mmd.domain.DiaryVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class CommentRequest {

    @Getter
    @AllArgsConstructor
    public static class CreateComment {
        private Long groupId;
        private Long level;
        private Long upperId;
        @NotBlank
        private String content;
        private CommentVisibility commentVisibility;
    }

    @Getter
    @AllArgsConstructor
    public static class UpdateComment {
        @NotBlank
        private String content;
        private CommentVisibility commentVisibility;
    }
}
