package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject", uniqueConstraints = { @UniqueConstraint(columnNames = "subject_name") })
@Getter
@Setter
@ToString
public class SubjectEntity {

    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subjectId;

    @Column(name = "subject_name", length = 50)
    private String subjectName;

    @OneToMany(mappedBy = "subjectId")
    private List<MarkEntity> subjectMarks;

    @OneToMany(mappedBy = "teacherSubjectId")
    private List<UserEntity> subjectTeachers;

    @OneToMany(mappedBy = "lessonSubject")
    private List<LessonEntity> lessons;

}
