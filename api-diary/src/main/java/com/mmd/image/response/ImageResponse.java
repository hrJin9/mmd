package com.mmd.image.response;

import com.mmd.image.dto.ImageFindResultDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ImageResponse {
    @Getter
    @RequiredArgsConstructor
    public static class FindImages {
        private final Long diaryId;
        private final String imageUrl;

        public static FindImages from(ImageFindResultDto imageFindResultDto) {
            return new FindImages(
                    imageFindResultDto.getDiaryId(),
                    imageFindResultDto.getImageUrl()
            );
        }
    }
}
