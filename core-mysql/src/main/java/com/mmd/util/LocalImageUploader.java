package com.mmd.util;

import com.mmd.exception.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalImageUploader {
    private final Environment environment;
    private static final String IMAGE_DIR = "image-dir";

    public String getFileName(MultipartFile file) {
        return createFileName(file.getOriginalFilename());
    }

    public String uploadAndGetPath(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());
        String path = environment.getProperty(IMAGE_DIR) + fileName;
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            log.error("file upload 에러: {}", e.getMessage());
            throw new FileException("잘못된 파일 요청입니다.");
        }

        return path;
    }

    private String createFileName(String originFileName) {
        UUID uuid = UUID.randomUUID();
        return uuid + "_" + originFileName;
    }
}
