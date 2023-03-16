package com.my.contactbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
@Getter
@Setter
@ToString
public class MarkDTO {
    private double markValue;

    private String studentCode;

    private String teacherCode;

    private long subjectId;

    private String type;

    private String semester;
}
