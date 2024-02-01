package com.mmd.diary.request;

import com.mmd.domain.DiaryVisibility;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DiaryRequest {
    @ApiModel(description = "다이어리 작성 request parameter")
    @Getter
    public static class CreateDiary {
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

    @ApiModel(description = "다이어리 수정 request parameter")
    @Getter
    public static class UpdateDiary {
        @ApiModelProperty(value = "제목", example = "사실 어제 일기입니다 ㅎㅎ")
        private String subject;

        @ApiModelProperty(value = "내용", example = "어제는 자전거를 탔어욥")
        private String contents;

        @ApiModelProperty(value = "공개 여부", example = "PUBLIC")
        private DiaryVisibility diaryVisibility;
    }

}
