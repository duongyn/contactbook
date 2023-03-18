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

    private long lessonId;

    private String scheduleTime;

    private String scheduleFrom;

    private String scheduleTo;

    private String className;
}
