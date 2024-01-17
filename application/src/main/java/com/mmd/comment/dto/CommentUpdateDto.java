package com.mmd.comment.dto;

import com.mmd.domain.Visibility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUpdateDto {
    private final Long memberId;
    private final Long commentId;
    private final String content;
    private final Visibility visibility;
}
