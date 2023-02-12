package com.my.contactbook.mapper;

import com.my.contactbook.dto.SubjectDTO;
import com.my.contactbook.entity.SubjectEntity;
import com.my.contactbook.exception.UserException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectMapper {

    private static final Logger logger = LoggerFactory.getLogger(SubjectMapper.class);
    @Autowired
    ModelMapper modelMapper;

    public SubjectDTO convertToDto(SubjectEntity subject) {
        try {
            SubjectDTO dto = modelMapper.map(subject, SubjectDTO.class);

            return dto;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

    }

    public SubjectEntity convertToEntity(SubjectDTO dto) {
        try {
            SubjectEntity entity = modelMapper.map(dto, SubjectEntity.class);

            return entity;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }

    public List<SubjectDTO> toListDto(List<SubjectEntity> listEntity) {
        List<SubjectDTO> listDto = new ArrayList<>();

        listEntity.forEach(e -> {
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
