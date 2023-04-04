package com.my.contactbook.mapper;

import com.my.contactbook.dto.AttendanceDTO;
import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.entity.AttendanceEntity;
import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.exception.UserException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttendaceMapper {
    private static final Logger logger = LoggerFactory.getLogger(AttendaceMapper.class);

    @Autowired
    ModelMapper modelMapper;

    public AttendanceDTO convertToDto(AttendanceEntity entity) {
        try {
            AttendanceDTO dto = modelMapper.map(entity, AttendanceDTO.class);
            dto.setUserCode(entity.getAttendUser().getUserCode());
            dto.setUsername(entity.getAttendUser().getUsername());
            dto.setFullName(entity.getAttendUser().getFirstName()+ " "+entity.getAttendUser().getLastName());
            dto.setScheduleId(entity.getAttendSchedule().getScheduleId());
            dto.setScheduleTime(entity.getAttendSchedule().getScheduleTime().toString());
            dto.setClassName(entity.getAttendSchedule().getClassId().getClassName());
            dto.setIsAttended(String.valueOf(entity.isAttended()));
            return dto;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

    }

    public AttendanceEntity convertToEntity(AttendanceDTO dto) {
        try {
            AttendanceEntity entity = modelMapper.map(dto, AttendanceEntity.class);

            return entity;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new RuntimeException("Class grade must be a number");
        }
    }

    public List<AttendanceDTO> toListDto(List<AttendanceEntity> listEntity) {
        List<AttendanceDTO> listDto = new ArrayList<>();

        listEntity.forEach(e -> {
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
