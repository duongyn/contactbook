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

    private String attendDate;

    private String attendYear;

    private String username;

    private String fullName;

    private String className;

    private String checkBy;

    private String isAttended;

}
