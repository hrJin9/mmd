package com.mmd.comment;

import com.mmd.comment.dto.CommentResponse;
import com.mmd.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long diaryNo, Long memberNo) {
        commentRepository.f

    }
}
