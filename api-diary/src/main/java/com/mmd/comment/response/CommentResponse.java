package com.mmd.comment.response;

import com.mmd.comment.dto.CommentFindResultDto;
import com.mmd.comment.request.CommentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommentResponse {
    @Getter
    @AllArgsConstructor
    public static class ViewComments {
        private String contents;
        private Long writerNo;
        private String writerNickName;

        public static CommentResponse.ViewComments from(CommentFindResultDto serviceDto) {
            return new CommentResponse.ViewComments(
                    serviceDto.getContents(),
                    serviceDto.getWriterNo(),
                    serviceDto.getWriterNickName()
            );
        }
    }

}
