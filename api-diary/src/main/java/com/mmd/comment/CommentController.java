package com.mmd.comment;

import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.comment.mapper.ServiceDtoMapper;
import com.mmd.comment.request.CommentRequest;
import com.mmd.comment.response.CommentResponse;
import com.mmd.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/diary/{diaryId}/comments")
    public ResponseEntity<List<CommentResponse.ViewComments>> getComments(@PathVariable Long diaryId) {
        List<CommentFindResultDto> comments = commentService.getComments(diaryId);
        List<CommentResponse.ViewComments> commentsResponse = comments.stream()
                .map(CommentResponse.ViewComments::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(commentsResponse);
    }

    @PostMapping("/diary/{diaryId}/comments")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal MemberDetails memberDetails,
                                              @PathVariable Long diaryId,
                                              @RequestBody @Valid CommentRequest.CreateComment request) {
        commentService.createComment(ServiceDtoMapper.mapping(memberDetails.getId(), diaryId, request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
