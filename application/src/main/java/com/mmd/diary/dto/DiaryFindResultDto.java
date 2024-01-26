package com.mmd.diary.dto;

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

    public static DiaryFindResultDto from(Diary diary) {
        return new DiaryFindResultDto(
                diary.getWriter().getId(),
                diary.getWriter().getNickName(),
                diary.getSubject(),
                diary.getContents()
        );
    }
}
