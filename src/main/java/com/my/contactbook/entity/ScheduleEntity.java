package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "schedule_slot")
    private SlotEntity scheduleSlot;

    @OneToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private ClassEntity classId;


}
