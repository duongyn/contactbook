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

    @Column(name = "mark_value")
    private double markValue;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subjectId;

    @Column(name = "mark_type", length = 50)
    private String markType;

    @Column(name = "semester", length = 50)
    private String semester;

}
