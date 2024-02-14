package com.mmd.image;

import com.mmd.image.dto.ImageFindResultDto;
import com.mmd.image.response.ImageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "이미지 API")
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @ApiOperation(value = "다이어리 목록 이미지 조회", notes = "조회한 다이어리 목록들의 이미지 목록을 조회합니다.", tags = "이미지 API")
    @ApiImplicitParam(name = "diaryIds", value = "다이어리 번호", example = "1,2,7", dataType = "List", paramType = "query")
    @GetMapping
    public ResponseEntity<List<ImageResponse.FindImages>> findDiaryImages(@RequestParam List<Long> diaryIds) {
        List<ImageFindResultDto> imageUrls = imageService.findDiaryImages(diaryIds);

        List<ImageResponse.FindImages> response = imageUrls.stream()
                .map(ImageResponse.FindImages::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(response);
    }

    @ApiOperation(value = "특정 다이어리의 이미지 목록 조회", notes = "조회한 다이어리 한 개의 이미지를 모두 가져옵니다.", tags = "이미지 API")
    @ApiImplicitParam(name = "diaryId", value = "다이어리 번호", example = "1", paramType = "path")
    @GetMapping("/{diaryId}")
    public ResponseEntity<List<String>> findOneDiaryImages(@PathVariable Long diaryId) {
        List<String> response = imageService.findOneDiaryImages(diaryId);
        return ResponseEntity.ok()
                .body(response);
    }

    @ApiOperation(value = "다이어리 이미지 등록", notes = "한 다이어리에 이미지를 등록합니다.", tags = "이미지 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diaryId", value = "다이어리 번호", example = "1", paramType = "path"),
            @ApiImplicitParam(name = "images", value = "첨부 이미지", dataType = "List" , paramType = "formData")
    })
    @PostMapping("/{diaryId}")
    public ResponseEntity<Void> createOneDiaryImages(@PathVariable Long diaryId,
                                                     @RequestPart List<MultipartFile> images) {
        imageService.createOneDiaryImages(diaryId, images);
        return ResponseEntity.created(URI.create("/api/image/" + diaryId)).build();
    }


    @ApiOperation(value = "다이어리 이미지 수정", notes = "다이어리에 첨부된 이미지를 수정합니다", tags = "이미지 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diaryId", value = "다이어리 번호", dataType = "Long", example = "1", paramType = "path"),
            @ApiImplicitParam(name = "deleteFileNames", value = "삭제할 이미지 파일 이름", example = "mmd/2024-02-07-16-15-12-641583.png, mmd/2024-02-07-15-51-16-791883.png", dataType = "List", paramType = "query"),
            @ApiImplicitParam(name = "images", value = "첨부 이미지", dataType = "List", paramType = "formData")
    })
    @PatchMapping("/{diaryId}")
    public ResponseEntity<Void> updateDiaryImages(@PathVariable Long diaryId,
                                                  @RequestParam(required = false) List<String> deleteFileNames,
                                                  @RequestPart(required = false) List<MultipartFile> images) {
        imageService.updateDiaryImages(diaryId, deleteFileNames, images);
        return ResponseEntity.created(URI.create("/api/image/" + diaryId)).build();
    }


}
