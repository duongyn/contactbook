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
public class ClassDTO {

    private long id;
    private String className;

    private String classGrade;

    private String formTeacherCode;

    private List<String> listStudentCode;

    private List<String> listStudentName;
}
