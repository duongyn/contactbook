package com.my.contactbook.service;

import com.my.contactbook.dto.AttendanceDTO;
import com.my.contactbook.entity.AttendanceEntity;
import com.my.contactbook.entity.ScheduleEntity;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.mapper.AttendaceMapper;
import com.my.contactbook.repository.AttendanceRepository;
import com.my.contactbook.repository.ClassRepository;
import com.my.contactbook.repository.ScheduleRepository;
import com.my.contactbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class AttendanceService {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendaceMapper attendaceMapper;

    public List<AttendanceDTO> findAll(){
        return attendaceMapper.toListDto(attendanceRepository.findAll());
    }

    public List<AttendanceDTO> findByUser(String userCode){
        UserEntity user = userRepository.findById(userCode).orElseThrow(() -> new RuntimeException("Not found user "+userCode));
        List<AttendanceEntity> list = attendanceRepository.findByAttendUser(user);
        return !list.isEmpty() ? attendaceMapper.toListDto(list) : null;
    }

    public AttendanceDTO findByUserAndDate(String userCode, String date) {
        UserEntity user = userRepository.findById(userCode).orElseThrow(() -> new RuntimeException("Not found user "+userCode));
        LocalDate attendDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        AttendanceEntity entity = attendanceRepository.findByAttendUserAndAttendDate(user, attendDate).orElseThrow(() -> new RuntimeException("Not found"));
        return attendaceMapper.convertToDto(entity);
    }

    public AttendanceDTO createAttendance(AttendanceDTO dto) {
        AttendanceEntity entity = attendaceMapper.convertToEntity(dto);
        entity.setAttended(dto.getIsAttended().equalsIgnoreCase("true"));
        UserEntity student = userRepository.findById(dto.getUserCode()).orElseThrow(() -> new RuntimeException("Not found user "+dto.getUserCode()));
        entity.setAttendUser(student);
        if(student.getStudentClass() == null) {
            throw new RuntimeException("Học sinh chưa có lớp! Không thể điểm danh");
        }
        LocalDate date = LocalDate.parse(dto.getAttendDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        entity.setAttendDate(date);

        if(!attendanceRepository.existsByAttendUserAndAttendDate(entity.getAttendUser(), entity.getAttendDate())){
            AttendanceEntity created = attendanceRepository.save(entity);
            return attendaceMapper.convertToDto(created);
        }
        else{
            return updateAttendance(dto);
        }
    }

    public boolean checkUserAttend(String userCode, String attendDate) {
        UserEntity student = userRepository.findById(userCode).orElseThrow(() -> new RuntimeException("Not found user "+userCode));
        LocalDate date = LocalDate.parse(attendDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        AttendanceEntity entity = attendanceRepository.findByAttendUserAndAttendDate(student, date)
                .orElseThrow(() -> new RuntimeException("Not found attendance object"));
        return entity.isAttended();
    }

    public AttendanceDTO updateAttendance(AttendanceDTO dto) {

        UserEntity student = userRepository.findById(dto.getUserCode()).orElseThrow(() -> new RuntimeException("Not found user "+dto.getUserCode()));
        LocalDate date = LocalDate.parse(dto.getAttendDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        AttendanceEntity entity = attendanceRepository.findByAttendUserAndAttendDate(student, date)
                .orElseThrow(() -> new RuntimeException("Not found attendance object"));
        entity.setAttended(dto.getIsAttended().equalsIgnoreCase("true"));
        entity.setAttendUser(student);
        AttendanceEntity updated = attendanceRepository.save(entity);
        return attendaceMapper.convertToDto(updated);

    }
}
