package com.my.contactbook.mapper;

import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.dto.UserEditDTO;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.exception.UserException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

    @Autowired
    ModelMapper modelMapper;

    public UserDTO convertToDto(UserEntity user) {
        try {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);

            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            userDTO.setDob(user.getDob().format(formatterDate));
            if(user.getJoinedDate() != null ){
                userDTO.setJoinedDate(user.getJoinedDate().format(formatterDate));
            }
            List<String> roles = new ArrayList<>();
            roles.add(user.getRoles().get(0).getRoleName());
            userDTO.setRoleName(roles);
            if(user.getStudentClass() != null) {
                userDTO.setStudentClass(String.valueOf(user.getStudentClass().getClassName()));
            }

            return userDTO;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

    }

    public UserEntity convertToEntity(UserDTO userDTO) {
        try {
            UserEntity user = modelMapper.map(userDTO, UserEntity.class);
            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            user.setDob(LocalDate.parse(userDTO.getDob(), formatterDate));
            if(userDTO.getJoinedDate() != null){
                user.setJoinedDate(LocalDate.parse(userDTO.getJoinedDate(), formatterDate));
            }
            user.setFirstName(user.getFirstName().trim());
            user.setLastName(user.getLastName().trim());
            return user;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }

    public List<UserDTO> toListDto(List<UserEntity> listEntity) {
        List<UserDTO> listDto = new ArrayList<>();

        listEntity.forEach(e -> {
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }

    public List<UserEntity> toListEntity(List<UserDTO> listDto) {
        List<UserEntity> listEntity = new ArrayList<>();

        listDto.forEach(e -> {
            listEntity.add(this.convertToEntity(e));
        });
        return listEntity;
    }

    public UserEditDTO convertEditToDto(UserEntity user) {
        try {
            UserEditDTO userDTO = modelMapper.map(user, UserEditDTO.class);

            return userDTO;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

    }

    public UserEntity convertEditToEntity(UserEditDTO userDTO) {
        try {
            UserEntity user = modelMapper.map(userDTO, UserEntity.class);
            user.setFirstName(user.getFirstName().trim());
            user.setLastName(user.getLastName().trim());
            return user;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
        }
    }

    public List<UserEditDTO> toListEditDto(List<UserEntity> listEntity) {
        List<UserEditDTO> listDto = new ArrayList<>();

        listEntity.forEach(e -> {
            listDto.add(this.convertEditToDto(e));
        });
        return listDto;
    }

}
