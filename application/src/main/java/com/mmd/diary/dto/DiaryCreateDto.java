package com.mmd.diary.dto;

import com.mmd.domain.DiaryVisibility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DiaryCreateDto {
    private final Long memberId;
    private final String subject;
    private final String contents;
    private final DiaryVisibility diaryVisibility;
}
