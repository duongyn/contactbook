package com.my.contactbook.service;

import com.my.contactbook.dto.ScheduleDTO;
import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.LessonEntity;
import com.my.contactbook.entity.ScheduleEntity;
import com.my.contactbook.mapper.ClassMapper;
import com.my.contactbook.mapper.ScheduleMapper;
import com.my.contactbook.repository.ClassRepository;
import com.my.contactbook.repository.LessonRepository;
import com.my.contactbook.repository.ScheduleRepository;
import com.my.contactbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    public ScheduleDTO createSchedule(ScheduleDTO dto) {
        ScheduleEntity schedule = scheduleMapper.convertToEntity(dto);
        LessonEntity lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new RuntimeException("Not found lesson with id: " + dto.getLessonId()));
        schedule.setLesson(lesson);
        ClassEntity classEntity = classRepository.findByClassName(dto.getClassName())
                .orElseThrow(() -> new RuntimeException("Not found class with id: " + dto.getClassName()));
        schedule.setClassId(classEntity);

        return scheduleMapper.convertToDto(scheduleRepository.save(schedule));
    }

    public ScheduleDTO findSchedule(long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Not found schedule with id: " + scheduleId));
        return scheduleMapper.convertToDto(schedule);
    }
}
