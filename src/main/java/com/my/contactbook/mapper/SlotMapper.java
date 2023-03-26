package com.my.contactbook.mapper;

import com.my.contactbook.dto.SlotDTO;
import com.my.contactbook.dto.SubjectDTO;
import com.my.contactbook.entity.SlotEntity;
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
public class SlotMapper {
    private static final Logger logger = LoggerFactory.getLogger(SlotMapper.class);
    @Autowired
    ModelMapper modelMapper;

    public SlotDTO convertToDto(SlotEntity slot) {
        try {
            SlotDTO dto = modelMapper.map(slot, SlotDTO.class);

            return dto;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

    }

    public SlotEntity convertToEntity(SlotDTO dto) {
        try {
            SlotEntity entity = modelMapper.map(dto, SlotEntity.class);

            return entity;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }

    public List<SlotDTO> toListDto(List<SlotEntity> listEntity) {
        List<SlotDTO> listDto = new ArrayList<>();

        listEntity.forEach(e -> {
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
