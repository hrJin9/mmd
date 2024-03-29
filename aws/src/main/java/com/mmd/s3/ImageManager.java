package com.mmd.s3;

import org.springframework.web.multipart.MultipartFile;

public interface ImageManager {
    String uploadImages(MultipartFile images);

    void deleteImages(String fileName);

    String findImageUrl(String fileName);
}
