package com.mmd.diary.request;

import com.mmd.domain.DiaryVisibility;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "다이어리 작성 요청 parameter")
@Getter
public class DiaryCreateRequest {
    @ApiModelProperty(value = "제목", required = true, example = "오늘의 일기")
    @NotBlank
    private String subject;

    @ApiModelProperty(value = "내용", required = true, example = "카페에서 먹은 커피예요.")
    @NotBlank
    private String contents;

    @ApiModelProperty(value = "공개 여부", required = true, example = "FRIEND")
    @NotNull
    private DiaryVisibility diaryVisibility;
}
