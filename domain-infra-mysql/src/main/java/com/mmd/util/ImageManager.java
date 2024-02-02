package com.mmd.util;

import com.mmd.exception.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageManager {
    private final Environment environment;
    private static final String FILE_DIR = "file-dir";

    public String getFileName(MultipartFile file) {
        return createFileName(file.getOriginalFilename());
    }

    public String uploadAndGetPath(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());
        String path = createPath(fileName);
        try {
            file.transferTo(new File(path));
            log.info("file uploaded successfully");
        } catch (IOException e) {
            log.error("file upload 에러: {}", e.getMessage());
            throw new FileException("잘못된 파일 요청입니다.");
        }

        return path;
    }

    public byte[] compressImage(MultipartFile file) {
        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            log.error("file bytes 에러: {}", e.getMessage());
            throw new FileException("잘못된 파일 요청입니다.");
        }

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while(!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            log.error("outStream close 에러: {}", e.getMessage());
            throw new FileException("잘못된 파일 요청입니다.");
        }

        return outputStream.toByteArray();
    }

    public byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];

        try {
            while(!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            throw new RuntimeException(e);
        }

        return outputStream.toByteArray();
    }

    private String createFileName(String originFileName) {
        UUID uuid = UUID.randomUUID();
        return uuid + "_" + originFileName;
    }

    private String createPath(String fileName) {
        return environment.getProperty(FILE_DIR) + fileName;
    }

}
