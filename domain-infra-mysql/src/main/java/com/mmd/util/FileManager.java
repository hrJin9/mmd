package com.mmd.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileManager {
    private final Environment environment;
    private static final String FILE_DIR = "file-dir";

    public String getFileName(MultipartFile file) {
        return createFileName(file.getOriginalFilename());
    }

    public String uploadAndGetFilePath(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());
        File newFile = new File(createPath(fileName));
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFile.getAbsolutePath();
    }

    private String createFileName(String originFileName) {
        UUID uuid = UUID.randomUUID();
        return uuid + "_" + originFileName;
    }

    private String createPath(String fileName) {
        return environment.getProperty(FILE_DIR) + fileName;
    }

}
