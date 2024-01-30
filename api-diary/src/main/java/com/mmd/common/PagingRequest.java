package com.mmd.common;

import com.mmd.domain.Criteria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = "페이징 요청 request parameter")
@Getter
public class PagingRequest {
    @ApiModelProperty(value = "페이지 번호", example = "0")
    private final Integer pageNo;

    @ApiModelProperty(value = "페이지 사이즈", example = "10")
    private final Integer size;

    @ApiModelProperty(value = "정렬 기준", example = "createdDate")
    private final Criteria criteria;

    // TODO : 기본값 세팅을 dto에서 해줘도 되는걸까?
    public PagingRequest(Integer pageNo, Integer size, Criteria criteria) {
        this.pageNo = pageNo == null ? 0 : pageNo;
        this.size = size == null ? 10 : size;
        this.criteria = criteria == null ? Criteria.CREATED_DATE : criteria;
    }

    public PageDto toServiceDto() {
        return new PageDto(
                this.pageNo,
                this.size,
                this.criteria
        );
    }
}
