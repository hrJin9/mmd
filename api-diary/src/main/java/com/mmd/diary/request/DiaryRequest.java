package com.mmd.diary.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DiaryRequest {
    @Getter
    @AllArgsConstructor
    public static class WriteRequest {
        private String subject;
        private String contents;

    }


}
