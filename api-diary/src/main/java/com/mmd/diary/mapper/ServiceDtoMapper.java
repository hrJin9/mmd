package com.mmd.diary.mapper;

import com.mmd.diary.dto.DiaryAttachmentDto;
import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.diary.request.DiaryCreateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ServiceDtoMapper {
    public static DiaryCreateDto mapping(Long memberId, DiaryCreateRequest request) {
        return new DiaryCreateDto(
                memberId,
                request.getSubject(),
                request.getContents(),
                request.getDiaryVisibility()
        );
    }

    public static DiaryAttachmentDto mapping(List<MultipartFile> files) {
//        if(Objects.isNull(files)) {
//            files = new ArrayList<>();
//        }

        return new DiaryAttachmentDto(files);
    }
}
