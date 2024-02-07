package com.mmd.image;

import com.mmd.image.dto.ImageFindResultDto;
import com.mmd.image.response.ImageResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api("이미지 API")
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "다이어리 목록 이미지 조회", description = "조회한 다이어리 목록들의 이미지 목록을 조회합니다.", tags = "이미지 API")
    @GetMapping
    public ResponseEntity<List<ImageResponse.FindImages>> findDiaryImages(@RequestParam List<Long> diaryIds) {
        List<ImageFindResultDto> imageUrls = imageService.findDiaryImages(diaryIds);

        List<ImageResponse.FindImages> response = imageUrls.stream()
                .map(ImageResponse.FindImages::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(response);
    }

    @Operation(summary = "특정 다이어리의 이미지 조회", description = "조회한 다이어리 한 개의 이미지를 모두 가져옵니다.", tags = "이미지 API")
    @GetMapping("/{diaryId}")
    public ResponseEntity<List<String>> findOneDiaryImages(@PathVariable Long diaryId) {
        List<String> response = imageService.findOneDiaryImages(diaryId);
        return ResponseEntity.ok()
                .body(response);
    }

    @Operation(summary = "다이어리 이미지 등록", description = "한 다이어리에 이미지를 등록합니다.", tags = "이미지 API")
    @PostMapping("/{diaryId}")
    public ResponseEntity<List<String>> createOneDiaryImages(@PathVariable Long diaryId,
                                                             @RequestPart List<MultipartFile> images) {
        imageService.createOneDiaryImages(diaryId, images);
        return ResponseEntity.created(URI.create("/api/image/" + diaryId)).build();
    }


    @Operation(summary = "다이어리 이미지 수정", description = "다이어리에 첨부된 이미지를 수정합니다.", tags = "이미지 API")
    @PatchMapping("/{diaryId}")
    public ResponseEntity<Void> updateDiaryImages(@PathVariable Long diaryId,
                                                  @RequestParam(required = false) List<String> deleteFileNames,
                                                  @RequestPart(required = false) List<MultipartFile> images) {
        imageService.updateDiaryImages(diaryId, deleteFileNames, images);
        return ResponseEntity.created(URI.create("/api/image/" + diaryId)).build();
    }


}
