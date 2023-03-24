package com.my.contactbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class PostDTO {
    private long postId;

    private String postName;

    private String createdBy;

    private String description;

    private String postDate;
}
