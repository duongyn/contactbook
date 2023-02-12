package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//@Entity
//@Table(name = "schedule")
//@Getter
//@Setter
//@ToString
public class ScheduleEntity {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long scheduleId;
    @Column(name = "schedule_time")
    private LocalDateTime scheduleTime;


}
