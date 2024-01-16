package com.mmd.comment;

import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.comment.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{diaryNo}")
    public ResponseEntity<List<CommentResponse.ViewComments>> getComments(@PathVariable Long diaryNo) {
        List<CommentFindResultDto> comments = commentService.getComments(diaryNo);
        List<CommentResponse.ViewComments> commentsResponse = comments.stream()
                .map(CommentResponse.ViewComments::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(commentsResponse);
    }

}
