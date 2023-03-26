package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class LessonEntity extends BaseEntity{
//    @Id
//    @Column(name = "lesson_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long lessonId;
//
//    @Column(name = "lesson_name")
//    private String lessonName;
//
//    @ManyToOne
//    @JoinColumn(name = "lesson_slot")
//    private SlotEntity lessonSlot;
//
//    private LocalDate lessonDate;
//
//    private String semester;
//
//    @ManyToOne
//    @JoinColumn(name = "lesson_subject")
//    private SubjectEntity lessonSubject;
//
////    @ManyToOne
////    @JoinColumn(name = "lesson_class")
////    private ClassEntity lessonClass;
//
//    @ManyToOne
//    @JoinColumn(name = "lesson_teacher")
//    private UserEntity lessonTeacher;
//
//    @OneToOne(mappedBy = "lesson")
//    private ScheduleEntity schedule;

}
