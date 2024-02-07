package com.mmd.image.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageFindResultDto {
    private final Long diaryId;
    private final String imageUrl;

    public static ImageFindResultDto of(Long diaryId, String imageUrl) {
        return new ImageFindResultDto(
                diaryId,
                imageUrl
        );
    }
}
