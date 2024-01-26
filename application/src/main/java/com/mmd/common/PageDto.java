package com.mmd.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public class PageDto {
    private final int pageNo;
    private final int pageSize;
    private final String criteria;

    public Pageable toPageable() {
        return PageRequest.of(
                this.pageNo,
                this.pageSize,
                Sort.by(Sort.Direction.DESC, this.criteria)
        );
    }
}
