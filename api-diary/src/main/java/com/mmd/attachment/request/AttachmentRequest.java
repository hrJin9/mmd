package com.mmd.attachment.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AttachmentRequest {
    @ApiModel("다이어리 목록에 대한 이미지 조회 request parameter")
    @Getter
    public static class FindDiaryImages {
        @NotNull
        List<Long> diaryIds;
    }

}
