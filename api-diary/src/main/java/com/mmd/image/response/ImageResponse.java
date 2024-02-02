package com.mmd.image.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ImageResponse {

    @Getter
    @RequiredArgsConstructor
    public static class FindImages {
        private final Long diaryId;
        private final Long imageId;
        private final String imageUrl;
    }

    @Getter
    @RequiredArgsConstructor
    public static class FindOneImages {
        private final Long imageId;
        private final String imageUrl;
    }


}
