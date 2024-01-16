package com.mmd.comment.dto;

import com.mmd.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentFindResultDto {
    private final String contents;
    private final Long writerNo;
    private final String writerNickName;

    public static CommentFindResultDto from(Comment comment) {
        return new CommentFindResultDto(
                comment.getContents(),
                comment.getWriter().getMemberNo(),
                comment.getWriter().getNickName()
        );
    }
}
