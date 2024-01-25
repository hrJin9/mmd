package com.mmd.diary;

import com.mmd.diary.dto.DiaryAttachmentDto;
import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.diary.mapper.ServiceDtoMapper;
import com.mmd.diary.request.DiaryRequest;
import com.mmd.security.MemberDetails;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api("다이어리 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @Operation(summary = "다이어리 작성", description = "다이어리를 작성합니다.", tags = "다이어리 API")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createDiary(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @RequestPart(value = "writeRequest") @Valid DiaryRequest.WriteRequest request,
                                            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        DiaryCreateDto diaryCreateDto = ServiceDtoMapper.mapping(memberDetails.getId(), request);
        DiaryAttachmentDto diaryAttachmentDto = ServiceDtoMapper.mapping(files);

        Long diaryId = diaryService.createDiary(diaryCreateDto, diaryAttachmentDto);

        return ResponseEntity.created(URI.create("/api/diary/" + diaryId)).build();
    }


    @Operation(summary = "다이어리 삭제", description = "특정 다이어리를 삭제합니다.", tags = "다이어리 API")
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @PathVariable Long diaryId) {

        diaryService.deleteDiary(memberDetails.getId(), diaryId);
        return ResponseEntity.noContent().build();
    }

}
