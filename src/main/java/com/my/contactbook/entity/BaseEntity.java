package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column
    private LocalDateTime createdDate;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column
    private LocalDateTime lastUpdatedDate;

    @Column
    private String lastUpdatedBy;
}
