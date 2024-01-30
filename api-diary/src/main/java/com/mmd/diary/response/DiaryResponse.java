package com.mmd.diary.response;

import com.mmd.diary.dto.DiaryFindResultDto;
import com.mmd.domain.DiaryVisibility;
import com.mmd.domain.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DiaryResponse {

    /* 다이어리 목록 조회 response parameter */
    @Getter
    @AllArgsConstructor
    public static class FindDiaries {
        private Long writerId;
        private String writerNickName;
        private String subject;
        private String contents;
        private DiaryVisibility diaryVisibility;
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
    public static class FindOneDiary {
        private Long writerId;
        private String writerNickName;
        private String subject;
        private String contents;
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
