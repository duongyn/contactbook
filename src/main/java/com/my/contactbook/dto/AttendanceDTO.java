package com.my.contactbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class AttendanceDTO {

    private long attendId;

    private String userCode;

    private String username;

    private String fullName;

    private long scheduleId;

    private String scheduleTime;

    private String className;

    private String checkBy;

    private String isAttended;

}
