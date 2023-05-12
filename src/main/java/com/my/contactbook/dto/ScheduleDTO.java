package com.my.contactbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class ScheduleDTO {
    private long scheduleId;

    private String scheduleTime;

    private String slotName;

    private String scheduleFrom;

    private String scheduleTo;

    private String className;

    private String subjectName;

    private String subjectGrade;

    private String teacherName;

    private String attendance;
}
