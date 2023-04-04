package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "attendance_db")
@Getter
@Setter
@ToString
public class AttendanceEntity extends BaseEntity{

    @Id
    @Column(name = "attend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attendId;

    @ManyToOne
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    private UserEntity attendUser;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
    private ScheduleEntity attendSchedule;

    @Column
    private boolean isAttended;

    private String checkBy;

}
