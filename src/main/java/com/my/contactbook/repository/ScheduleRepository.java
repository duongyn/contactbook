package com.my.contactbook.repository;

import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.ScheduleEntity;
import com.my.contactbook.entity.SlotEntity;
import com.my.contactbook.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    Boolean existsByScheduleTimeAndScheduleSlotAndClassIdAndSubject(LocalDate scheduleTime, SlotEntity scheduleSlot, ClassEntity classId, SubjectEntity subject);

    Boolean existsByScheduleTimeAndScheduleSlotAndClassId(LocalDate scheduleTime, SlotEntity scheduleSlot, ClassEntity classId);

    List<ScheduleEntity> findByClassId(ClassEntity classId);

}
