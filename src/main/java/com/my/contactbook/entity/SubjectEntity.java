package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject")
@Getter
@Setter
@ToString
public class SubjectEntity extends BaseEntity{

    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subjectId;

    @Column(name = "subject_name", length = 50)
    private String subjectName;

    @Column(name = "subject_grade")
    private String subjectGrade;

    @OneToMany(mappedBy = "subjectId")
    private List<MarkEntity> subjectMarks;

    @ManyToMany
    @JoinTable(name = "teacher_subject", joinColumns = @JoinColumn(name = "user_code"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<UserEntity> teachers;

//    @OneToMany(mappedBy = "lessonSubject")
//    private List<LessonEntity> lessons;

}
