package com.mmd.diary;

import com.mmd.common.PagingRequest;
import com.mmd.diary.dto.DiaryAttachmentDto;
import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.diary.dto.DiaryFindResultDto;
import com.mmd.diary.mapper.ServiceDtoMapper;
import com.mmd.diary.request.DiaryCreateRequest;
import com.mmd.diary.response.DiaryResponse;
import com.mmd.security.MemberDetails;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api("다이어리 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @Operation(summary = "모든 다이어리 목록 조회", description = "로그인한 사용자가 접근할 수 있는 다른 사용자의 다이어리 목록을 페이징 조회합니다.", tags = "다이어리 API")
    @GetMapping
    public ResponseEntity<List<DiaryResponse.FindDiaries>> findAllDiaries(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                          PagingRequest request) {
        List<DiaryFindResultDto> diaries = diaryService.findAllDiaries(memberDetails.getId(), request.toServiceDto());
        List<DiaryResponse.FindDiaries> response = diaries.stream()
                .map(DiaryResponse.FindDiaries::from)
                .collect(Collectors.toList());

        // TODO : 이미지는 따로 조회
        return ResponseEntity.ok()
                .body(response);
    }

    @Operation(summary = "유저의 다이어리 목록 조회", description = "해당 사용자가 작성한 다이어리 목록을 페이징 조회합니다.", tags = "다이어리 API")
    @GetMapping("/{memberId}")
    public ResponseEntity<List<DiaryResponse.FindDiaries>> findMemberDiaries(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                             @PathVariable Long memberId,
                                                                             PagingRequest request) {
        List<DiaryFindResultDto> diaries = diaryService.findMemberDiaries(memberDetails.getId(), memberId, request.toServiceDto());
        List<DiaryResponse.FindDiaries> response = diaries.stream()
                .map(DiaryResponse.FindDiaries::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(response);
    }

    @Operation(summary = "다이어리 조회", description = "특정 다이어리 내용을 조회합니다.", tags = "다이어리 API")
    @GetMapping("/{memberId}/{diaryId}")
    public ResponseEntity<DiaryResponse.FindOneDiary> findOneDiary(@AuthenticationPrincipal MemberDetails memberDetails,
                                                      @PathVariable Long memberId,
                                                      @PathVariable Long diaryId) {
        DiaryFindResultDto response = diaryService.findOneDiary(memberDetails.getId(), memberId, diaryId);
        // TODO : 코멘트, 첨부파일은 따로 조회
        return ResponseEntity.ok()
                .body(DiaryResponse.FindOneDiary.from(response));
    }

//    @Operation(summary = "다이어리 임시 저장", description = "다이어리 작성 중 임시저장합니다.", tags = "다이어리 API")
//    @PostMapping("/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})


    @Operation(summary = "다이어리 작성", description = "다이어리를 작성합니다.", tags = "다이어리 API")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createDiary(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @RequestPart(value = "writeRequest") @Valid DiaryCreateRequest request,
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
