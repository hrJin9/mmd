package com.mmd.attachment;

import com.mmd.attachment.request.AttachmentRequest;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("첨부파일 API")
@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @Operation(summary = "다이어리 목록 이미지 조회", description = "조회한 다이어리 목록들의 이미지 목록을 조회합니다.", tags = "첨부파일 API")
    @GetMapping("/images")
    public ResponseEntity<> findDiaryImages(@RequestBody @Valid AttachmentRequest.FindDiaryImages request) {
        findDiaryImages(request.getDiaryIds());

    }

    @Operation(summary = "특정 다이어리의 첨부파일 조회", description = "조회한 다이어리 한 개의 첨부파일을 모두 가져옵니다.", tags = "첨부파일 API")
    @GetMapping("/{diaryId}")
    public ResponseEntity<> findOneDiaryFiles(@PathVariable Long diaryId) {
        attachmentService.findOneDiaryFiles(diaryId);
    }

}
