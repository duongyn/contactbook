package com.my.contactbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class MarkDTO {

    private long markId;
    private double halfMark;
    private double semesterMark;
    private double finalMark;
    private String halfFeedback;
    private String semesterFeedback;
    private String studentCode;
    private String teacherCode;
    private long markSubjectId;
    private String subjectName;
    private String teacherName;
    private String studentName;
    private String semester;

}
