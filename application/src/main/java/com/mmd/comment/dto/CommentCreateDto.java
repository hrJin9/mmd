package com.mmd.comment.dto;

import com.mmd.domain.Visibility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCreateDto {
    private final Long groupId;
    private final Long level;
    private final Long upperId;
    private final String contents;
    private final Long diaryId;
    private final Long memberId;
    private final Visibility visibility;
}
