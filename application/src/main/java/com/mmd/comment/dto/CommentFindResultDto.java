package com.mmd.comment.dto;

import com.mmd.domain.Visibility;
import com.mmd.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentFindResultDto {
    private final String contents;
    private final Long writerId;
    private final String writerNickName;
    private final Visibility visibility;

    public static CommentFindResultDto from(Comment comment) {
        return new CommentFindResultDto(
                comment.getContent(),
                comment.getWriter().getId(),
                comment.getWriter().getNickName(),
                comment.getVisibility()
        );
    }
}
