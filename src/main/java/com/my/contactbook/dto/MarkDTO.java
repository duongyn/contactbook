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

    private long markId;
    private double markValue;

    private String studentCode;

    private String teacherCode;

    private long markSubjectId;

    private String subjectName;

    private String teacherName;

    private String studentName;

    private String markType;

    private String coefficient;

    private String semester;
}
