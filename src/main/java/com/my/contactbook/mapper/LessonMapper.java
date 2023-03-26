package com.my.contactbook.mapper;

import com.my.contactbook.dto.LessonDTO;
import com.my.contactbook.entity.LessonEntity;
import com.my.contactbook.exception.UserException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonMapper {
    private static final Logger logger = LoggerFactory.getLogger(LessonMapper.class);

//    @Autowired
//    ModelMapper modelMapper;
//
//    public LessonDTO convertToDto(LessonEntity entity) {
//        try {
//            LessonDTO dto = modelMapper.map(entity, LessonDTO.class);
//            dto.setLessonSubjectName(entity.getLessonSubject().getSubjectName());
//            dto.setLessonSubjectGrade(entity.getLessonSubject().getSubjectGrade());
//            dto.setLessonTeacherCode(entity.getLessonTeacher().getUserCode());
//            dto.setLessonSlotId(entity.getLessonSlot().getSlotId());
//            return dto;
//        } catch (Exception ex) {
//            logger.warn(ex.getMessage());
//            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
//        }
//
//    }
//
//    public LessonEntity convertToEntity(LessonDTO dto) {
//        try {
//            LessonEntity entity = modelMapper.map(dto, LessonEntity.class);
//
//            return entity;
//        } catch (Exception ex) {
//            logger.warn(ex.getMessage());
//            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
//        }
//    }
//
//    public List<LessonDTO> toListDto(List<LessonEntity> listEntity) {
//        List<LessonDTO> listDto = new ArrayList<>();
//
//        listEntity.forEach(e -> {
//            listDto.add(this.convertToDto(e));
//        });
//        return listDto;
//    }
}
