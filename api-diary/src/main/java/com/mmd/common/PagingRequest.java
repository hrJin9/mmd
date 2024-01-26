package com.mmd.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PagingRequest {
    private final int pageNo;
    private final int pageSize;
    private final String criteria;

    public PageDto toServiceDto() {
        return new PageDto(
                this.pageNo,
                this.pageSize,
                this.criteria
        );
    }
}
