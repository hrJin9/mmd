package com.mmd.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mmd.exception.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3ImageUploader implements ImageManager {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSSS");
    private static final String DIRECTORY_DELIMS = "/";
    private static final String EXTENSION_DELIMS = ".";

    @Value("${aws.s3.bucket}")
    private String BUCKET;

    @Value("${aws.s3.dir}")
    private String DIRECTORY;

    @Value("${aws.s3.base-url}")
    private String BASE_URL;

    private final AmazonS3 s3;

    @Override
    public String uploadImages(MultipartFile image) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(image.getContentType());
        objectMetadata.setContentLength(image.getSize());
        String formattedFileName = formatFileName(image.getOriginalFilename());
        try {
            s3.putObject(
                    new PutObjectRequest(
                            BUCKET,
                            DIRECTORY + DIRECTORY_DELIMS + formattedFileName,
                            image.getInputStream(),
                            objectMetadata
                    )
            );
        } catch (IOException e) {
            log.error("이미지 저장 실패");
            throw new FileException("이미지 저장에 실패하였습니다.");
        }
        return BASE_URL +  DIRECTORY_DELIMS + DIRECTORY + DIRECTORY_DELIMS + formattedFileName;
    }

    @Override
    public void deleteImages(String fileName) {
        s3.deleteObject(new DeleteObjectRequest(
                BUCKET,
                fileName
        ));
    }

    @Override
    public String findImageUrl(String fileName) {
        return s3.getUrl(
                BUCKET,
                fileName
        ).toString();
    }

    private String formatFileName(String originalFileName) {
        return FORMATTER.format(LocalDateTime.now()) + originalFileName.substring(originalFileName.lastIndexOf(EXTENSION_DELIMS));
    }


}
