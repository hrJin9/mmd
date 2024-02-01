package com.mmd.attachment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AttachmentDto {
//    private final String fileName;
//    private final String originalFileName;
//    private final Long fileSize;
    private final List<MultipartFile> files;
}
