package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "class_db", uniqueConstraints = {@UniqueConstraint(columnNames = "class_name")})
@Getter
@Setter
@ToString
public class ClassEntity extends BaseEntity{

    @Id
    @Column(name = "class_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long classId;

    @Column(name = "class_name", length = 50)
    private String className;

    @OneToMany(mappedBy = "studentClass")
    private List<UserEntity> studentList;

    @OneToOne
    @JoinColumn(name = "form_teacher")
    private UserEntity formTeacher;

    private long classGrade;

    @OneToOne(mappedBy = "classId")
    private ScheduleEntity classSchedule;

//    @OneToMany(mappedBy = "lessonClass")
//    private List<LessonEntity> lessons;

}
