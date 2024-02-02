package com.mmd.diary.mapper;

import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.diary.dto.DiaryUpdateDto;
import com.mmd.diary.request.DiaryRequest;

public class ServiceDtoMapper {
    public static DiaryCreateDto mapping(Long memberId, DiaryRequest.CreateDiary request) {
        return new DiaryCreateDto(
                memberId,
                request.getSubject(),
                request.getContents(),
                request.getDiaryVisibility()
        );
    }

    public static DiaryUpdateDto mapping(Long diaryId, DiaryRequest.UpdateDiary request) {
        return new DiaryUpdateDto(
                diaryId,
                request.getSubject(),
                request.getContents(),
                request.getDiaryVisibility()
        );
    }
}
