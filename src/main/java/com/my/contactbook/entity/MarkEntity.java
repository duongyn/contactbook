package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "mark")
@Getter
@Setter
@ToString
public class MarkEntity extends BaseEntity{
    @Id
    @Column(name = "mark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long markId;
    @Column(name = "half_mark")
    private double halfMark;
    @Column(name = "semester_mark")
    private double semesterMark;
    @Column(name = "final_mark")
    private double finalMark;
    @Column(name = "half_feedback")
    private String halfFeedback;
    @Column(name = "semester_feedback")
    private String semesterFeedback;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subjectId;
    @Column(name = "semester", length = 50)
    private String semester;

}
