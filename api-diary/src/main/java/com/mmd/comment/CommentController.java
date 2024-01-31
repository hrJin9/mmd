package com.mmd.comment;

import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.comment.mapper.ServiceDtoMapper;
import com.mmd.comment.request.CommentRequest;
import com.mmd.comment.response.CommentResponse;
import com.mmd.diary.DiaryService;
import com.mmd.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    
    /* 다이어리의 코멘트 조회 */
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
    
    /* 코멘트 작성 */
    @PostMapping("/{diaryId}")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal MemberDetails memberDetails,
                                              @PathVariable Long diaryId,
                                              @RequestBody @Valid CommentRequest.CreateComment request) {
        commentService.createComment(ServiceDtoMapper.mapping(memberDetails.getId(), diaryId, request));
        return ResponseEntity.created(URI.create("/api/comment/" + diaryId)).build();
    }

    /* 코멘트 수정 */
    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal MemberDetails memberDetails,
                                              @PathVariable Long commentId,
                                              @RequestBody @Valid CommentRequest.UpdateComment request) {
        commentService.updateComment(ServiceDtoMapper.mapping(memberDetails.getId(), commentId, request));
        return ResponseEntity.ok().build();
    }
    
    /* 코멘트 삭제 */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal MemberDetails memberDetails,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(memberDetails.getId(), commentId);
        return ResponseEntity.noContent().build();
    }
}
