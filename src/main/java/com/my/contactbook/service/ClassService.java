package com.my.contactbook.service;

import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.mapper.ClassMapper;
import com.my.contactbook.repository.ClassRepository;
import com.my.contactbook.repository.UserRepository;
import com.my.contactbook.util.ExcelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

    public ClassDTO findClass(long classId) {
        return classMapper.convertToDto(classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Not found Class.")));
    }

    public List<ClassDTO> getAll(){
        return classMapper.toListDto(classRepository.findAll());
    }

    public ClassDTO createClass(ClassDTO dto) {
        ClassEntity existClassName = classRepository.findByClassName(dto.getClassName()).orElse(null);
        if(existClassName != null){
            throw new RuntimeException("Error: Class "+dto.getClassName()+" exists.");
        }
        ClassEntity entity = classMapper.convertToEntity(dto);
        //find form teacher from db, then add to class
        if(dto.getFormTeacherCode() != null){
            UserEntity teacher = userRepository.findById(dto.getFormTeacherCode())
                    .orElseThrow(() -> new RuntimeException("Error: Teacher ID is not found."));
            if(classRepository.existsByFormTeacher(teacher)){
                throw new RuntimeException("Error: Teacher has not available");
            }
            if (teacher.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase("TEACHER"))) {
                entity.setFormTeacher(teacher);
            } else {
                throw new RuntimeException("Error: Not a correct form teacher.");
            }
        }
        //save class to database
        if(dto.getListStudentCode() != null){
            return addStudentToClass(classRepository.save(entity), dto.getListStudentCode());
        }
        else{
            return addStudentToClass(classRepository.save(entity), new ArrayList<>());
        }
    }

    public ClassDTO updateStudentsClass(long classId, List<String> studentCodes) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Error: Class is not found."));
        return addStudentToClass(classEntity, studentCodes);
    }

    public ClassDTO updateTeacherClass(long classId, String teacherCode) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Error: Class is not found."));
        UserEntity teacher = userRepository.findById(teacherCode)
                .orElseThrow(() -> new RuntimeException("Error: Teacher ID is not found."));
        if(classRepository.existsByFormTeacher(teacher)){
            throw new RuntimeException("Error: Teacher has not available");
        }
        if (teacher.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase("TEACHER"))) {
            classEntity.setFormTeacher(teacher);
        } else {
            throw new RuntimeException("Error: Not a correct form teacher.");
        }
        return classMapper.convertToDto(classRepository.save(classEntity));
    }

    public ByteArrayInputStream getStudentsFromDb() {
        List<UserEntity> students = userRepository.findAll();

        ByteArrayInputStream in = ExcelHelper.studentsToExcel(students);
        return in;
    }

    public void addStudentFromExcel(long classId, MultipartFile file) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Error: Class is not found."));
        if(ExcelHelper.hasExcelFormat(file)) {
            try {
                List<UserEntity> list = ExcelHelper.excelToStudents(file.getInputStream());
                List<UserEntity> validList = new ArrayList<>();
                for(UserEntity user : list){
                    if(userRepository.checkValidUser(user.getUserCode(), user.getFirstName(), user.getLastName()) == 1 && user.getUserCode().contains("HS")){
                        UserEntity validUser = userRepository.findById(user.getUserCode()).orElse(null);
                        if(validUser != null){
                            validList.add(validUser);
                            validUser.setStudentClass(classEntity);
                            userRepository.save(validUser);
                        }
                    }
//                    else {
//                        throw new RuntimeException("Error: User "+user.getUserCode()+" not match in db");
//                    }
                }
                classEntity.setStudentList(validList);
                classRepository.save(classEntity);
            } catch (IOException exception) {
                throw new RuntimeException("fail to store excel data: " + exception.getMessage());
            }
        }
    }

    public ClassDTO addStudentToClass(ClassEntity entity, List<String> studentCodes) {
        ClassEntity classEntity = classRepository.findById(entity.getClassId()).orElseThrow(() -> new RuntimeException("Error: Class is not found."));
        //find student list from db, then add to class
        List<UserEntity> listStudent = new ArrayList<>();
        if(studentCodes != null && studentCodes.size() > 0){
            for (String studentCode : studentCodes) {
                UserEntity userEntity = userRepository.findById(studentCode)
                        .orElseThrow(() -> new RuntimeException("Error: Student ID is not found."));
                if (userEntity.getRoles().stream().anyMatch(role -> role.getRolePrefix().equalsIgnoreCase("HS"))) {
                    listStudent.add(userEntity);
                }
                //update student: add class object to 'student_class' field in database
                userEntity.setStudentClass(classEntity);
                userRepository.save(userEntity);
            }
        }
        if (listStudent.size() > 0) {
            classEntity.setStudentList(listStudent);
        }
        return classMapper.convertToDto(classRepository.save(classEntity));
    }

}
