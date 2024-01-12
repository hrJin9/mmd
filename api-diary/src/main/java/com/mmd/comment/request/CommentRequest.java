package com.mmd.comment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommentRequest {
    @Getter
    @AllArgsConstructor
    public static class CreateComment {
        private String contents;
    }
}
