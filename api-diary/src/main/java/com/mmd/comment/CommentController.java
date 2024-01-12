package com.mmd.comment;

import com.mmd.comment.dto.CommentResponse;
import com.mmd.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{diaryNo}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long diaryNo,
                                        @AuthenticationPrincipal MemberDetails memberDetails) {
        List<CommentResponse> comments = commentService.getComments(diaryNo, memberDetails.getMemberNo());
        return ResponseEntity.ok().body(comments);
    }




}
