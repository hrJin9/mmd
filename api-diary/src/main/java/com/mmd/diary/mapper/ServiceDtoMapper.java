package com.mmd.diary.mapper;

import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.diary.request.DiaryCreateRequest;

public class ServiceDtoMapper {
    public static DiaryCreateDto mapping(Long memberId, DiaryCreateRequest request) {
        return new DiaryCreateDto(
                memberId,
                request.getSubject(),
                request.getContents(),
                request.getDiaryVisibility()
        );
    }
}
