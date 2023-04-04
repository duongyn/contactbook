package com.my.contactbook.repository;

import com.my.contactbook.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findByAttendUser(UserEntity attendUser);
    List<AttendanceEntity> findByAttendSchedule(ScheduleEntity attendSchedule);

    Boolean existsByAttendUserAndAttendSchedule(UserEntity attendUser, ScheduleEntity attendSchedule);

    Optional<AttendanceEntity> findByAttendUserAndAttendSchedule(UserEntity attendUser, ScheduleEntity attendSchedule);
}
