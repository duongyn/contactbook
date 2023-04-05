package com.my.contactbook.mapper;

import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.dto.MarkDTO;
import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.MarkEntity;
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
public class MarkMapper {
    private static final Logger logger = LoggerFactory.getLogger(MarkMapper.class);

    @Autowired
    ModelMapper modelMapper;

    public MarkDTO convertToDto(MarkEntity entity) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            MarkDTO dto = modelMapper.map(entity, MarkDTO.class);
            dto.setStudentCode(entity.getUserId().getUserCode());
            dto.setTeacherCode(entity.getCreatedBy());
            dto.setTeacherName(entity.getCreatedBy());
            dto.setStudentName(entity.getUserId().getFirstName()+" "+entity.getUserId().getLastName());
            dto.setSubjectName(entity.getSubjectId().getSubjectName());
            dto.setMarkSubjectId(entity.getSubjectId().getSubjectId());
            return dto;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

    }

    public MarkEntity convertToEntity(MarkDTO dto) {
        try {
            MarkEntity entity = modelMapper.map(dto, MarkEntity.class);

            return entity;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }

    public List<MarkDTO> toListDto(List<MarkEntity> listEntity) {
        List<MarkDTO> listDto = new ArrayList<>();

        listEntity.forEach(e -> {
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
