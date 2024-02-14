package com.mmd.image.response;

import com.mmd.image.dto.ImageFindResultDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ImageResponse {
    @Getter
    @RequiredArgsConstructor
    @ApiModel(description = "이미지 조회 response data")
    public static class FindImages {
        @ApiModelProperty(value = "다이어리 번호", example = "1")
        private final Long diaryId;
        @ApiModelProperty(value = "이미지 url", example = "https://d3vc1h087xuxhg.cloudfront.net/mmd/2024-02-07-16-15-05-857223.png")
        private final String imageUrl;

        public static FindImages from(ImageFindResultDto imageFindResultDto) {
            return new FindImages(
                    imageFindResultDto.getDiaryId(),
                    imageFindResultDto.getImageUrl()
            );
        }
    }
}
