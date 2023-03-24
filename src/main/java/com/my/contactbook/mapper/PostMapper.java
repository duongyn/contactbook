package com.my.contactbook.mapper;

import com.my.contactbook.dto.MarkDTO;
import com.my.contactbook.dto.PostDTO;
import com.my.contactbook.entity.MarkEntity;
import com.my.contactbook.entity.PostEntity;
import com.my.contactbook.exception.UserException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {

    private static final Logger logger = LoggerFactory.getLogger(MarkMapper.class);

    @Autowired
    ModelMapper modelMapper;

    public PostDTO convertToDto(PostEntity entity) {
        try {
            PostDTO dto = modelMapper.map(entity, PostDTO.class);
            return dto;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

    }

    public PostEntity convertToEntity(PostDTO dto) {
        try {
            PostEntity entity = modelMapper.map(dto, PostEntity.class);

            return entity;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }

    public List<PostDTO> toListDto(List<PostEntity> listEntity) {
        List<PostDTO> listDto = new ArrayList<>();

        listEntity.forEach(e -> {
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
