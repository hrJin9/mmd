package com.mmd.comment;

import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.entity.Comment;
import com.mmd.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentFindResultDto> getComments(Long diaryNo) {
        List<Comment> comments = commentRepository.findAllComment(diaryNo);
        return comments.stream()
                .map(CommentFindResultDto::from)
                .collect(Collectors.toList());
    }

}
