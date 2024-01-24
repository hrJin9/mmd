package com.mmd.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileManager {
    private final Environment environment;
    private static final String FILE_DIR = "file-dir";

    public String uploadFile(MultipartFile file) {
        String savedFileName = createFileName(file.getOriginalFilename());

        try {
            file.transferTo(new File(createPath(savedFileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return savedFileName;
    }

    private String createFileName(String originFileName) {
        UUID uuid = UUID.randomUUID();
        return uuid + "_" + originFileName;
    }

    private String createPath(String fileName) {
        return environment.getProperty(FILE_DIR) + fileName;
    }
}
