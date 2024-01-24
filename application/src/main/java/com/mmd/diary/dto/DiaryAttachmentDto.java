package com.mmd.diary.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class DiaryAttachmentDto {
    private String fileName;
    private String orgFileName;
    private Long fileSize;
    private List<MultipartFile> files;

    public DiaryAttachmentDto(List<MultipartFile> files) {
        this.files = files;
    }
}
