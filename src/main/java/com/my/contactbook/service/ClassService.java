package com.my.contactbook.service;

import com.my.contactbook.dto.ClassDTO;
import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.SubjectEntity;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.mapper.ClassMapper;
import com.my.contactbook.mapper.UserMapper;
import com.my.contactbook.repository.ClassRepository;
import com.my.contactbook.repository.SubjectRepository;
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
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private UserMapper userMapper;

    public ClassDTO findClass(long classId) {
        return classMapper.convertToDto(classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Not found Class.")));
    }

    public ClassDTO findClassByName(String name) {
        return classMapper.convertToDto(classRepository.findByClassName(name).orElseThrow(() -> new RuntimeException("Not found class")));
    }

    public List<ClassDTO> getAll() {
        List<ClassEntity> list = classRepository.findAll();
        List<ClassEntity> validList = new ArrayList<>();
        for(ClassEntity c: list){
            if(!c.isDeleted()){
                validList.add(c);
            }
        }
        return classMapper.toListDto(validList);
    }

    public String checkValidNameAndGrade(String name, long grade){
        String nameNumber = name.replaceAll("[a-zA-Z]+", "");
        String nameWord = name.replaceAll("\\d+", "");
        String message = "";
        if(Integer.parseInt(nameNumber) == grade && grade <= 5 && grade > 0){
            message = "";
        }
        else{
            message = "Class grade must be the same as the number in class name";
            return message;
        }
        if(nameWord.matches("^[a-zA-Z]$")){
            message = "";
        }
        else{
            message = "Class name must contain only 1 number and 1 character from a to z";
        }
        return message;
    }

    public ClassDTO createClass(ClassDTO dto) {
        ClassEntity existClassName = classRepository.findByClassName(dto.getClassName()).orElse(null);
        if (existClassName != null) {
            if(existClassName.isDeleted()){
                existClassName.setDeleted(false);
                return addStudentToClass(classRepository.save(existClassName), new ArrayList<>());
            }
            else{
                throw new RuntimeException("Error: Class " + dto.getClassName() + " exists.");
            }
        }
        ClassEntity entity = classMapper.convertToEntity(dto);
        entity.setDeleted(false);
        //find form teacher from db, then add to class
        if (dto.getFormTeacherCode() != null) {
            UserEntity teacher = userRepository.findById(dto.getFormTeacherCode())
                    .orElseThrow(() -> new RuntimeException("Error: Teacher ID is not found."));
            if (classRepository.existsByFormTeacher(teacher)) {
                throw new RuntimeException("Error: Teacher has not available");
            }
            if (teacher.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase("TEACHER"))) {
                entity.setFormTeacher(teacher);
            } else {
                throw new RuntimeException("Error: Not a correct form teacher.");
            }
        }
        String message = checkValidNameAndGrade(entity.getClassName(), entity.getClassGrade());
        if(!message.isEmpty()){
            throw new RuntimeException(message);
        }
        ClassEntity createdClass = classRepository.save(entity);
        addSubjectForTeacher(createdClass, null);
        //save class to database
        if (dto.getListStudentCode() != null) {
            return addStudentToClass(createdClass, dto.getListStudentCode());
        } else {
            return addStudentToClass(createdClass, new ArrayList<>());
        }
    }

    public ClassDTO updateStudentsClass(long classId, List<String> studentCodes) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Error: Class is not found."));
        return addStudentToClass(classEntity, studentCodes);
    }

    public List<UserDTO> getValidTeachers() {
        List<UserEntity> list = userRepository.findAll();
        List<UserEntity> validList = new ArrayList<>();
        for (UserEntity user : list) {
            if (!classRepository.existsByFormTeacher(user) && !user.isDeleted() && user.getRoles().get(0).getRolePrefix().equals("GV")) {
                validList.add(user);
            }
        }
        return validList != null ? userMapper.toListDto(validList) : null;
    }

    public List<UserDTO> getValidStudents() {
        List<UserEntity> list = userRepository.findAll();
        List<UserEntity> validList = new ArrayList<>();
        for (UserEntity user : list) {
            if (user.getStudentClass() == null && !user.isDeleted() && user.getRoles().get(0).getRolePrefix().equals("HS")) {
                validList.add(user);
            }
        }
        return validList != null ? userMapper.toListDto(validList) : null;
    }

    public void addSubjectForTeacher(ClassEntity classEntity,UserEntity teacher) {
        List<SubjectEntity> list = subjectRepository.findBySubjectGrade(String.valueOf(classEntity.getClassGrade()));
        if(!list.isEmpty()){
            classEntity.setClassSubjects(list);
            if(teacher != null) {
                teacher.setTeacherSubjects(list);
                userRepository.save(teacher);
            }
            classRepository.save(classEntity);
        }
    }

    public ClassDTO updateTeacherClass(ClassDTO classDTO) {
        ClassEntity classEntity = classRepository.findById(classDTO.getId()).orElseThrow(() -> new RuntimeException("Error: Class is not found."));
        classEntity.setClassName(classDTO.getClassName());
        try {
            classEntity.setClassGrade(Integer.parseInt(classDTO.getClassGrade()));
        } catch (Exception ex){
            throw new RuntimeException("Class grade must be a number");
        }

        String message = checkValidNameAndGrade(classEntity.getClassName(), classEntity.getClassGrade());
        if(!message.isEmpty()){
            throw new RuntimeException(message);
        }
        if(classDTO.getFormTeacherCode() != null){
            UserEntity teacher = userRepository.findById(classDTO.getFormTeacherCode())
                    .orElseThrow(() -> new RuntimeException("Error: Teacher ID is not found."));
            ClassEntity existedClass = classRepository.findByFormTeacher(teacher).orElse(null);
            if (existedClass != null && existedClass.getClassId() != classEntity.getClassId()) {
                throw new RuntimeException("Teacher has not available");
            }
            if (teacher.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase("TEACHER"))) {
                classEntity.setFormTeacher(teacher);
            } else {
                throw new RuntimeException("Not a correct form teacher.");
            }
            addSubjectForTeacher(classEntity, teacher);
        }
        return classMapper.convertToDto(classRepository.save(classEntity));
    }

    public ByteArrayInputStream getStudentsFromDb(List<UserDTO> studentsList) {
        List<UserEntity> list = new ArrayList<>();
        for (UserDTO dto : studentsList) {
            UserEntity user = userRepository.findById(dto.getUserCode()).orElse(null);
            if (user != null) {
                list.add(user);
            }
        }
        List<UserEntity> students = new ArrayList<>();
        for (UserEntity user : list) {
            if (user.getRoles().get(0).getRolePrefix().equals("HS") && !user.isDeleted()) {
                students.add(user);
            }
        }
        ByteArrayInputStream in = ExcelHelper.studentsToExcel(students);
        return in;
    }

    public void addStudentFromExcel(long classId, MultipartFile file) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Error: Class is not found."));
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                List<UserEntity> list = ExcelHelper.excelToStudents(file.getInputStream());
                List<UserEntity> validList = new ArrayList<>();
                for (UserEntity user : list) {
                    if (userRepository.checkValidUser(user.getUserCode(), user.getFirstName(), user.getLastName()) == 1
                            && user.getUserCode().contains("HS")
                    ) {
                        UserEntity validUser = userRepository.findById(user.getUserCode()).orElse(null);
                        ClassEntity existClass = validUser.getStudentClass();
                        if (validUser != null && existClass == null) {
                            validList.add(validUser);
                            validUser.setStudentClass(classEntity);
                            userRepository.save(validUser);
                        }
                        else {
                            throw new RuntimeException("Error: User "+validUser.getUserCode()+" already in a class");
                        }
                    }
                    else {
                        throw new RuntimeException("Error: User "+user.getUserCode()+"does not match in database");
                    }
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
        if (studentCodes != null && studentCodes.size() > 0) {
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

    public List<ClassDTO> searchClass(String name){
        List<ClassEntity> list = classRepository.findAll();
        List<ClassEntity> searchList = new ArrayList<>();
        for(ClassEntity c: list){
            if(!c.isDeleted() && c.getClassName().contains(name)){
                searchList.add(c);
            }
        }
        return classMapper.toListDto(searchList);
    }

    public ClassDTO getClassByTeacher(String teacherCode) {
        UserEntity teacher = userRepository.findById(teacherCode).orElseThrow(() -> new RuntimeException("Not found user"));
        ClassEntity entity = classRepository.findByFormTeacher(teacher).orElseThrow(() -> new RuntimeException("Not found class"));
        return classMapper.convertToDto(entity);
    }

    public void deleteClass(long classId) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Error: Class is not found."));
        if (!classEntity.isDeleted()) {
            classEntity.setDeleted(true);
            List<UserEntity> studentList = classEntity.getStudentList();
            for (UserEntity user : studentList) {
                user.setStudentClass(null);
            }
            classEntity.setFormTeacher(null);
            classEntity.setStudentList(null);
            classRepository.save(classEntity);
        }

    }

}
