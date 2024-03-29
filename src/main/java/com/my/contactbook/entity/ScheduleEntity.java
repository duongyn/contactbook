package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@ToString
public class ScheduleEntity extends BaseEntity{

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long scheduleId;

    @Column(name = "schedule_time")
    private LocalDate scheduleTime;

    @Column(name = "schedule_year")
    private String scheduleYear;

    @Column(name = "schedule_day")
    private String scheduleDay;

    @ManyToOne
    @JoinColumn(name = "schedule_slot")
    private SlotEntity scheduleSlot;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private ClassEntity classId;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private SubjectEntity subject;

}
