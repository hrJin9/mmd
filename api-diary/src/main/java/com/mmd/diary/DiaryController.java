package com.mmd.diary;

import com.mmd.common.PagingRequest;
import com.mmd.diary.dto.DiaryCreateDto;
import com.mmd.diary.dto.DiaryFindResultDto;
import com.mmd.diary.dto.DiaryUpdateDto;
import com.mmd.diary.mapper.ServiceDtoMapper;
import com.mmd.diary.request.DiaryRequest;
import com.mmd.diary.response.DiaryResponse;
import com.mmd.security.MemberDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "다이어리 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @ApiOperation(value = "모든 다이어리 목록 조회", notes = "로그인한 사용자가 접근할 수 있는 다른 사용자의 다이어리 목록을 페이징 조회합니다.", tags = "다이어리 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "페이징 request", paramType = "query")
    })
    @GetMapping
    public ResponseEntity<List<DiaryResponse.FindDiaries>> findAllDiaries(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                          PagingRequest request) {
        List<DiaryFindResultDto> diaries = diaryService.findAllDiaries(memberDetails.getId(), request.toServiceDto());
        List<DiaryResponse.FindDiaries> response = diaries.stream()
                .map(DiaryResponse.FindDiaries::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(response);
    }

    @ApiOperation(value = "유저의 다이어리 목록 조회", notes = "해당 사용자가 작성한 다이어리 목록을 페이징 조회합니다.", tags = "다이어리 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "조회할 유저", example = "7", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "페이징 request",  paramType = "query")
    })
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

    @ApiOperation(value = "다이어리 조회", notes = "특정 다이어리 내용을 조회합니다.", tags = "다이어리 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "조회할 유저 번호", example = "7", paramType = "path"),
            @ApiImplicitParam(name = "diaryId", value = "조회할 다이어리 번호", example = "3", paramType = "path")
    })
    @GetMapping("/{memberId}/{diaryId}")
    public ResponseEntity<DiaryResponse.FindOneDiary> findOneDiary(@AuthenticationPrincipal MemberDetails memberDetails,
                                                      @PathVariable Long memberId,
                                                      @PathVariable Long diaryId) {
        DiaryFindResultDto response = diaryService.findOneDiary(memberDetails.getId(), memberId, diaryId);
        return ResponseEntity.ok()
                .body(DiaryResponse.FindOneDiary.from(response));
    }

    @ApiOperation(value = "다이어리 작성", notes = "다이어리를 작성합니다.", tags = "다이어리 API")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createDiary(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @RequestBody @Valid DiaryRequest.CreateDiary request) {
        DiaryCreateDto diaryCreateDto = ServiceDtoMapper.mapping(memberDetails.getId(), request);
        Long diaryId = diaryService.createDiary(diaryCreateDto);

        return ResponseEntity.created(URI.create("/api/diary/" + diaryId)).build();
    }

    @ApiOperation(value = "다이어리 수정", notes = "다이어리 내용을 수정합니다.", tags = "다이어리 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diaryId", value = "수정할 다이어리 번호", example = "3", paramType = "path")
    })
    @PatchMapping(value = "/{diaryId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateDiary(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @PathVariable Long diaryId,
                                            @RequestBody @Valid DiaryRequest.UpdateDiary request) {
        DiaryUpdateDto diaryUpdateDto = ServiceDtoMapper.mapping(diaryId, request);
        int updateCount = diaryService.updateDiary(memberDetails.getId(), diaryUpdateDto);

        if(updateCount > 0) {
            return ResponseEntity.created(URI.create("/api/diary/" + diaryId)).build();
        }
        return ResponseEntity.noContent().build();
    }


    @ApiOperation(value = "다이어리 삭제", notes = "특정 다이어리를 삭제합니다.", tags = "다이어리 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diaryId", value = "삭제할 다이어리 번호", example = "3", paramType = "path")
    })
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(@AuthenticationPrincipal MemberDetails memberDetails,
                                            @PathVariable Long diaryId) {
        diaryService.deleteDiary(memberDetails.getId(), diaryId);
        return ResponseEntity.noContent().build();
    }

}
