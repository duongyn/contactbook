package com.my.contactbook.service;

import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.mapper.ClassMapper;
import com.my.contactbook.repository.ClassRepository;
import com.my.contactbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClassService {

    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ClassMapper classMapper;

    public ClassDTO findClass(long classId){
        return classMapper.convertToDto(classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Not found Class.")));
    }

    public ClassDTO createClass(ClassDTO dto){
        ClassEntity entity = classMapper.convertToEntity(dto);
        //find form teacher from db, then add to class
        UserEntity teacher = userRepository.findById(dto.getFormTeacherCode())
                .orElseThrow(() -> new RuntimeException("Error: Teacher ID is not found."));
        if(teacher.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase("FORM_TEACHER"))){
            entity.setFormTeacher(teacher);
        }
        else {
            throw new RuntimeException("Error: Not a correct form teacher.");
        }
        //save class to database
        return addStudentToClass(classRepository.save(entity), dto.getListStudentCode());
    }

    public ClassDTO addStudentToClass(ClassEntity entity, List<String> studentCodes){
        ClassEntity classEntity = classRepository.findById(entity.getClassId()).orElseThrow(() -> new RuntimeException("Error: Class is not found."));
        //find student list from db, then add to class
        List<UserEntity> listStudent = new ArrayList<>();
        for(String studentCode: studentCodes){
            UserEntity userEntity = userRepository.findById(studentCode)
                    .orElseThrow(() -> new RuntimeException("Error: Student ID is not found."));
            if(userEntity.getRoles().stream().anyMatch(role -> role.getRolePrefix().equalsIgnoreCase("HS"))){
                listStudent.add(userEntity);
            }
            //update student: add class object to 'student_class' field in database
            userEntity.setStudentClass(classEntity);
            userRepository.save(userEntity);
        }
        if(listStudent.size() > 0){
            classEntity.setStudentList(listStudent);
        }
        else {
            throw new RuntimeException("Error: Not found any student to add to class.");
        }
        return classMapper.convertToDto(classRepository.save(classEntity));
    }

}
