package com.my.contactbook.mapper;

import com.my.contactbook.dto.MarkDTO;
import com.my.contactbook.dto.ScheduleDTO;
import com.my.contactbook.entity.MarkEntity;
import com.my.contactbook.entity.ScheduleEntity;
import com.my.contactbook.exception.UserException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleMapper {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleMapper.class);

    @Autowired
    ModelMapper modelMapper;

    public ScheduleDTO convertToDto(ScheduleEntity entity) {
        try {
            ScheduleDTO dto = modelMapper.map(entity, ScheduleDTO.class);
            dto.setScheduleFrom(entity.getScheduleSlot().getFromTime().toString());
            dto.setScheduleTo(entity.getScheduleSlot().getToTime().toString());
            dto.setClassName(entity.getClassId().getClassName());
            dto.setSubjectName(entity.getSubject().getSubjectName());
            dto.setSubjectGrade(entity.getSubject().getSubjectGrade());
            dto.setSlotName(entity.getScheduleSlot().getSlotName());
            dto.setTeacherName(entity.getClassId().getFormTeacher().getFirstName()+" "+entity.getClassId().getFormTeacher().getLastName());
            return dto;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

    }

    public ScheduleEntity convertToEntity(ScheduleDTO dto) {
        try {
            ScheduleEntity entity = modelMapper.map(dto, ScheduleEntity.class);

            return entity;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }

    public List<ScheduleDTO> toListDto(List<ScheduleEntity> listEntity) {
        List<ScheduleDTO> listDto = new ArrayList<>();

        listEntity.forEach(e -> {
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
