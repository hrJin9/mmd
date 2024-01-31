package com.mmd.comment.request;

import com.mmd.domain.CommentVisibility;
import com.mmd.domain.DiaryVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentRequest {
    @Getter
    public static class CreateComment {
        private Long groupId;
        @NotNull
        private Long level;
        private Long upperId;
        @NotBlank
        private String content;
        @NotNull
        private CommentVisibility commentVisibility;
    }

    @Getter
    public static class UpdateComment {
        @NotBlank
        private String content;
        private CommentVisibility commentVisibility;
    }
}
