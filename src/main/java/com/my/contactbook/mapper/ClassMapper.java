package com.my.contactbook.mapper;

import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.exception.UserException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassMapper {
    private static final Logger logger = LoggerFactory.getLogger(ClassMapper.class);

    @Autowired
    ModelMapper modelMapper;

    public ClassDTO convertToDto(ClassEntity entity) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            ClassDTO dto = modelMapper.map(entity, ClassDTO.class);
            List<String> listStudentCode = new ArrayList<>();
            List<String> listStudentName = new ArrayList<>();
            if(entity.getStudentList() != null){
                for (UserEntity user : entity.getStudentList()) {
                    listStudentCode.add(user.getUserCode());
                }
                dto.setListStudentCode(listStudentCode);
                for (UserEntity user : entity.getStudentList()) {
                    listStudentName.add(user.getFirstName()+" "+user.getLastName());
                }
                dto.setListStudentName(listStudentName);
                dto.setFormTeacherCode(entity.getFormTeacher().getUserCode());
            }

            return dto;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

    }

    public ClassEntity convertToEntity(ClassDTO dto) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            ClassEntity entity = modelMapper.map(dto, ClassEntity.class);

            return entity;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new RuntimeException("Class grade must be a number");
        }
    }

    public List<ClassDTO> toListDto(List<ClassEntity> listEntity) {
        List<ClassDTO> listDto = new ArrayList<>();

        listEntity.forEach(e -> {
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
