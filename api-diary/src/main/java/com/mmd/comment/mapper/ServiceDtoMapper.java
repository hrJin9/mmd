package com.mmd.comment.mapper;

import com.mmd.comment.dto.CommentCreateDto;
import com.mmd.comment.request.CommentRequest;

public class ServiceDtoMapper {
    public static CommentCreateDto mapping(Long memberId, Long diaryId, CommentRequest.CreateComment request) {
        return new CommentCreateDto(
                request.getGroupId(),
                request.getLevel(),
                request.getUpperId(),
                request.getContents(),
                diaryId,
                memberId,
                request.getVisibility()
        );
    }


}
