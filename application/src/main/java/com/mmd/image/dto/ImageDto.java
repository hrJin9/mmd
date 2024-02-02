package com.mmd.image.dto;

import com.mmd.entity.Image;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageDto {
    private final String fileName;
    private final String originalFileName;
    private final String type;
    private final String path;
    private final byte[] data;

    public static ImageDto from(Image image) {
        return new ImageDto(
                image.getFileName(),
                image.getOriginalFileName(),
                image.getType(),
                image.getPath(),
                image.getData()
        );
    }
}
