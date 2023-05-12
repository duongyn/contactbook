package com.my.contactbook.service;

import com.my.contactbook.dto.ScheduleDTO;
import com.my.contactbook.dto.SlotDTO;
import com.my.contactbook.dto.SubjectDTO;
import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.ScheduleEntity;
import com.my.contactbook.entity.SlotEntity;
import com.my.contactbook.entity.SubjectEntity;
import com.my.contactbook.mapper.ScheduleMapper;
import com.my.contactbook.mapper.SlotMapper;
import com.my.contactbook.mapper.SubjectMapper;
import com.my.contactbook.repository.*;
import com.my.contactbook.util.ExcelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private SlotRepository slotRepository;

    @Autowired
    private SubjectRepository subjectRepository;

//    @Autowired
//    private LessonRepository lessonRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private SlotMapper slotMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    public List<ScheduleDTO> findAllSchedules() {
        List<ScheduleEntity> list = scheduleRepository.findAll();
        List<ScheduleEntity> validList = new ArrayList<>();
        for (ScheduleEntity s : list) {
            if (!s.isDeleted()) {
                validList.add(s);
            }
        }
        return scheduleMapper.toListDto(validList);
    }

    public List<ScheduleDTO> findSchedulesByTeacher(String userCode) {
        List<ScheduleEntity> list = scheduleRepository.findAll();
        List<ScheduleEntity> validList = new ArrayList<>();
        for (ScheduleEntity s : list) {
            if (!s.isDeleted() && s.getClassId().getFormTeacher() != null && s.getClassId().getFormTeacher().getUserCode().equals(userCode)) {
                validList.add(s);
            }
        }
        return scheduleMapper.toListDto(validList);
    }

    public List<SubjectDTO> getByClassName(String className){
        return subjectMapper.toListDto(subjectRepository.findByClassName(className));
    }

    public void addScheduleFromExcel(MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                List<ScheduleDTO> list = ExcelHelper.excelToSchedules(file.getInputStream());
                for (ScheduleDTO scheduleDTO : list) {
                    createSchedule(scheduleDTO);
                }
            } catch (IOException exception) {
                throw new RuntimeException("fail to store excel data: " + exception.getMessage());
            }
        }
    }

    public ScheduleDTO createSchedule(ScheduleDTO dto) {
        ScheduleEntity schedule = scheduleMapper.convertToEntity(dto);
//        LessonEntity lesson = lessonRepository.findById(dto.getLessonId())
//                .orElseThrow(() -> new RuntimeException("Not found lesson with id: " + dto.getLessonId()));
//        schedule.setLesson(lesson);
        ClassEntity classEntity = classRepository.findByClassName(dto.getClassName())
                .orElseThrow(() -> new RuntimeException("Not found class with id: " + dto.getClassName()));
        schedule.setClassId(classEntity);
        SlotEntity slot = slotRepository.findBySlotName(dto.getSlotName()).orElseThrow(() -> new RuntimeException("Not found slot"));
        schedule.setScheduleSlot(slot);
        SubjectEntity subject = subjectRepository.findBySubjectNameAndSubjectGrade(dto.getSubjectName(), dto.getSubjectGrade())
                .orElseThrow(() -> new RuntimeException("Not found subject "+dto.getSubjectName()));
        schedule.setSubject(subject);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        schedule.setScheduleTime(LocalDate.parse(dto.getScheduleTime(), df));
        if(scheduleRepository.existsByScheduleTimeAndScheduleSlotAndClassIdAndSubject(schedule.getScheduleTime(), schedule.getScheduleSlot(), schedule.getClassId(),subject)){
            throw new RuntimeException("Thời khóa biểu cho "+schedule.getScheduleTime().toString()+" của lớp "+schedule.getClassId().getClassName()+" đã tồn tại");
        }
        if(scheduleRepository.existsByScheduleTimeAndScheduleSlotAndClassId(schedule.getScheduleTime(), schedule.getScheduleSlot(), schedule.getClassId())){
            throw new RuntimeException("Thời khóa biểu cho "+schedule.getScheduleTime().toString()+" của lớp "+schedule.getClassId().getClassName()+" đã tồn tại");
        }
        return scheduleMapper.convertToDto(scheduleRepository.save(schedule));
    }

    public List<SlotDTO> getAllSlots() {
        return slotMapper.toListDto(slotRepository.findAll());
    }

    public void createDefaultSlots() {
        List<SlotEntity> entities = slotRepository.findAll();
        if (entities.isEmpty()) {
            SlotEntity slot1 = new SlotEntity("Slot 1", LocalTime.of(7, 0), LocalTime.of(8, 30));
            SlotEntity slot2 = new SlotEntity("Slot 2", LocalTime.of(8, 45), LocalTime.of(10, 15));
            SlotEntity slot3 = new SlotEntity("Slot 3", LocalTime.of(10, 30), LocalTime.of(12, 0));
            SlotEntity slot4 = new SlotEntity("Slot 4", LocalTime.of(13, 0), LocalTime.of(14, 30));
            SlotEntity slot5 = new SlotEntity("Slot 5", LocalTime.of(14, 45), LocalTime.of(16, 15));
            List<SlotEntity> list = new ArrayList<>();
            list.add(slot1);
            list.add(slot2);
            list.add(slot3);
            list.add(slot4);
            list.add(slot5);
            slotRepository.saveAll(list);
        }
    }

    public ScheduleDTO updateSchedule(ScheduleDTO dto) {
        ScheduleEntity schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Error: Not found schedule"));
//        LessonEntity lesson = lessonRepository.findById(dto.getLessonId())
//                .orElseThrow(() -> new RuntimeException("Not found lesson with id: " + dto.getLessonId()));
//        schedule.setLesson(lesson);
        ClassEntity classEntity = classRepository.findByClassName(dto.getClassName())
                .orElseThrow(() -> new RuntimeException("Not found class with id: " + dto.getClassName()));
        schedule.setClassId(classEntity);
        SlotEntity slot = slotRepository.findBySlotName(dto.getSlotName()).orElseThrow(() -> new RuntimeException("Not found slot"));
        schedule.setScheduleSlot(slot);
        SubjectEntity subject = subjectRepository.findBySubjectNameAndSubjectGrade(dto.getSubjectName(), dto.getSubjectGrade())
                .orElseThrow(() -> new RuntimeException("Not found subject "+dto.getSubjectName()));
        schedule.setSubject(subject);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        schedule.setScheduleTime(LocalDate.parse(dto.getScheduleTime(), df));
        if(scheduleRepository.existsByScheduleTimeAndScheduleSlotAndClassIdAndSubject(schedule.getScheduleTime(), schedule.getScheduleSlot(), schedule.getClassId(),subject)){
            throw new RuntimeException("Schedule exists in database");
        }
        return scheduleMapper.convertToDto(scheduleRepository.save(schedule));
    }

    public void deleteSchedule(long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Error: Not found schedule"));
        schedule.setDeleted(true);
        scheduleRepository.save(schedule);
    }

    public ScheduleDTO findSchedule(long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Not found schedule with id: " + scheduleId));
        return scheduleMapper.convertToDto(schedule);
    }

    public List<ScheduleDTO> findByClass(String className) {
        ClassEntity classId = classRepository.findByClassName(className).orElseThrow(() -> new RuntimeException("Not found class"));
        return scheduleMapper.toListDto(scheduleRepository.findByClassId(classId));
    }
}
