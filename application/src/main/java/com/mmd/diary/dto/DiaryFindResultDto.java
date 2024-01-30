package com.mmd.diary.dto;

import com.mmd.domain.DiaryVisibility;
import com.mmd.domain.FriendStatus;
import com.mmd.entity.Diary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DiaryFindResultDto {
    private final Long writerId;
    private final String writerNickName;
    private final String subject;
    private final String contents;
    private final DiaryVisibility diaryVisibility;
    private final Integer totalPages;

    public static DiaryFindResultDto from(Diary diary) {
        return new DiaryFindResultDto(
                diary.getWriter().getId(),
                diary.getWriter().getNickName(),
                diary.getSubject(),
                diary.getContents(),
                diary.getDiaryVisibility(),
                null
        );
    }

    public static DiaryFindResultDto of(Diary diary, int totalPages) {
        return new DiaryFindResultDto(
                diary.getWriter().getId(),
                diary.getWriter().getNickName(),
                diary.getSubject(),
                diary.getContents(),
                diary.getDiaryVisibility(),
                totalPages
        );
    }
}
