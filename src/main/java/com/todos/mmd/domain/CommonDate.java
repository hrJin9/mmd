package com.todos.mmd.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonDate {

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public CommonDate(LocalDateTime createDate, LocalDateTime lastModifiedDate) {
        this.createDate = createDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public CommonDate() {
        this(null, null);
    }
}
