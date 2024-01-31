package com.mmd.comment.mapper;

import com.mmd.comment.dto.CommentCreateDto;
import com.mmd.comment.dto.CommentUpdateDto;
import com.mmd.comment.request.CommentRequest;

public class ServiceDtoMapper {
    public static CommentCreateDto mapping(Long memberId, Long diaryId, CommentRequest.CreateComment request) {
        return new CommentCreateDto(
                diaryId,
                request.getGroupId(),
                request.getLevel(),
                request.getUpperId(),
                request.getContent(),
                memberId,
                request.getCommentVisibility()
        );
    }

    public static CommentUpdateDto mapping(Long memberId, Long commentId, CommentRequest.UpdateComment request) {
        return new CommentUpdateDto(
                memberId,
                commentId,
                request.getContent(),
                request.getCommentVisibility()
        );
    }

}
