package com.my.contactbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class LessonDTO {

    private String lessonName;

    private long lessonSlotId;

    private String semester;

    private String lessonSubjectName;

    private String lessonSubjectGrade;

    private String lessonTeacherCode;
}
