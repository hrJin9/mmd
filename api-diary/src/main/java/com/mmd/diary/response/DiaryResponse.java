package com.mmd.diary.response;

import com.mmd.diary.dto.DiaryFindResultDto;
import com.mmd.domain.DiaryVisibility;
import com.mmd.domain.FriendStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DiaryResponse {

    @Getter
    @AllArgsConstructor
    @ApiModel(value = "다이어리 목록 조회 response data")
    public static class FindDiaries {
        @ApiModelProperty(value = "작성자 번호", example = "1")
        private Long writerId;
        @ApiModelProperty(value = "작성자 닉네임", example = "달래")
        private String writerNickName;
        @ApiModelProperty(value = "다이어리 제목", example = "오늘의 일기")
        private String subject;
        @ApiModelProperty(value = "다이어리 내용", example = "나 이거이거 했다~")
        private String contents;
        @ApiModelProperty(value = "공개 여부", example = "FRIEND")
        private DiaryVisibility diaryVisibility;
        @ApiModelProperty(value = "총 페이지수", example = "20")
        private Integer totalPages;

        public static FindDiaries from(DiaryFindResultDto diaryFindResultDto) {
            return new FindDiaries(
                    diaryFindResultDto.getWriterId(),
                    diaryFindResultDto.getWriterNickName(),
                    diaryFindResultDto.getSubject(),
                    diaryFindResultDto.getContents(),
                    diaryFindResultDto.getDiaryVisibility(),
                    diaryFindResultDto.getTotalPages()
            );
        }
    }

    /* 다이어리 조회 response parameter */
    @Getter
    @AllArgsConstructor
    @ApiModel("다이어리 조회 response data")
    public static class FindOneDiary {
        @ApiModelProperty(value = "작성자 번호", example = "1")
        private Long writerId;
        @ApiModelProperty(value = "작성자 닉네임", example = "달래")
        private String writerNickName;
        @ApiModelProperty(value = "다이어리 제목", example = "오늘의 일기")
        private String subject;
        @ApiModelProperty(value = "다이어리 내용", example = "나 이거이거 했다~")
        private String contents;
        @ApiModelProperty(value = "공개 여부", example = "FRIEND")
        private DiaryVisibility diaryVisibility;

        public static FindOneDiary from(DiaryFindResultDto diaryFindResultDto) {
            return new FindOneDiary(
                    diaryFindResultDto.getWriterId(),
                    diaryFindResultDto.getWriterNickName(),
                    diaryFindResultDto.getSubject(),
                    diaryFindResultDto.getContents(),
                    diaryFindResultDto.getDiaryVisibility()
            );
        }

    }

}
