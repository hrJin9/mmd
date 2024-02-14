package com.mmd.comment;

import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.comment.mapper.ServiceDtoMapper;
import com.mmd.comment.request.CommentRequest;
import com.mmd.comment.response.CommentResponse;
import com.mmd.diary.DiaryService;
import com.mmd.security.MemberDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "댓글 API")
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    
    @ApiOperation(value = "댓글 목록 조회", notes = "로그인된 사용자가 접근할 수 있는 다이어리의 댓글을 가져옵니다.", tags = "댓글 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diaryId", value = "다이어리 번호", example = "1", dataType = "Long", paramType = "path")
    })
    @GetMapping("/{diaryId}")
    public ResponseEntity<List<CommentResponse.ViewComments>> findComments(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                          @PathVariable Long diaryId) {
        List<CommentFindResultDto> comments = commentService.findComments(memberDetails.getId(), diaryId);
        List<CommentResponse.ViewComments> commentsResponse = comments.stream()
                .map(CommentResponse.ViewComments::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(commentsResponse);
    }
    
    @ApiOperation(value = "댓글 작성", notes = "다이어리에 댓글을 작성합니다.", tags = "댓글 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diaryId", value = "다이어리 번호", example = "1", dataType = "Long", paramType = "path")
    })
    @PostMapping("/{diaryId}")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal MemberDetails memberDetails,
                                              @PathVariable Long diaryId,
                                              @RequestBody @Valid CommentRequest.CreateComment request) {
        commentService.createComment(ServiceDtoMapper.mapping(memberDetails.getId(), diaryId, request));
        return ResponseEntity.created(URI.create("/api/comment/" + diaryId)).build();
    }

    @ApiOperation(value = "댓글 수정", notes = "작성된 댓글을 수정합니다.", tags = "댓글 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글 번호", example = "1", dataType = "Long", paramType = "path")
    })
    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal MemberDetails memberDetails,
                                              @PathVariable Long commentId,
                                              @RequestBody @Valid CommentRequest.UpdateComment request) {
        commentService.updateComment(ServiceDtoMapper.mapping(memberDetails.getId(), commentId, request));
        return ResponseEntity.ok().build();
    }
    
    @ApiOperation(value = "댓글 삭제", notes = "작성된 댓글을 삭제합니다.", tags = "댓글 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글 번호", example = "1", dataType = "Long", paramType = "path")
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal MemberDetails memberDetails,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(memberDetails.getId(), commentId);
        return ResponseEntity.noContent().build();
    }
}
