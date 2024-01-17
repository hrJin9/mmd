package com.mmd.comment.dto;

import com.mmd.domain.CommentVisibility;
import com.mmd.domain.DiaryVisibility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCreateDto {
    private final Long groupId;
    private final Long level;
    private final Long upperId;
    private final String content;
    private final Long diaryId;
    private final Long memberId;
    private final CommentVisibility commentVisibility;
}
