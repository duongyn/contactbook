package com.my.contactbook.service;

import com.my.contactbook.dto.UserDTO;
import com.my.contactbook.dto.UserEditDTO;
import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.RoleEntity;
import com.my.contactbook.entity.SubjectEntity;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.exception.UserException;
import com.my.contactbook.mapper.UserMapper;
import com.my.contactbook.repository.ClassRepository;
import com.my.contactbook.repository.RoleRepository;
import com.my.contactbook.repository.SubjectRepository;
import com.my.contactbook.repository.UserRepository;
import com.my.contactbook.util.ExcelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserMapper userMapper;

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public UserDTO getUserByUsername(String username) {
        return userMapper.convertToDto(userRepository.findByUsername(username).orElse(null));
    }

    public UserDTO getUserByCode(String userCode) {
        return userMapper.convertToDto(userRepository.findById(userCode).orElse(null));
    }

    public void createDefaultAdmin() {
        List<UserEntity> list = userRepository.findAll();
        if (list.isEmpty()) {
            UserDTO user = new UserDTO();
            user.setFirstName("Duong");
            user.setLastName("Hai");
            user.setGender("Male");
            user.setDob("1998-12-11");
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            user.setRoleName(roles);
            user.setAddress("Hanoi");
            createUser(user);
        }
    }

    public UserDTO createUser(UserDTO user) {
        List<String> roles = user.getRoleName();
        List<RoleEntity> roleList = new ArrayList<>();
        UserEntity entity = userMapper.convertToEntity(user);
        if (roles == null) {
            RoleEntity userRole = roleRepository.findByRoleName("STUDENT")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roleList.add(userRole);
        } else {
            roles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        RoleEntity adminRole = roleRepository.findByRoleName("ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleList.add(adminRole);
                        break;
                    case "MANAGER":
                        RoleEntity formRole = roleRepository.findByRoleName("MANAGER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleList.add(formRole);
                        break;
                    case "TEACHER":
                        RoleEntity subjRole = roleRepository.findByRoleName("TEACHER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleList.add(subjRole);
                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByRoleName("STUDENT")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleList.add(userRole);
                }
            });
        }
        entity.setRoles(roleList);
        entity.setStatus(UserEntity.EStatus.ENABLE);
        if (user.getAddress() == null || user.getAddress().equalsIgnoreCase("undefined")
                || user.getAddress().length() == 0) {
            entity.setAddress("Hanoi");
        }
        String username = getGeneratedUsername(entity.getFirstName(), entity.getLastName());
        entity.setUsername(username);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String password = entity.getUsername() + "@" + entity.getDob().format(formatter);
        entity.setPassword(encoder.encode(password));
        entity.setDeleted(false);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());

        // save
        try {
            UserEntity saveUser = userRepository.save(entity);
            return userMapper.convertToDto(saveUser);
        } catch (Exception ex) {
            throw new UserException(UserException.USER_CREATE_DATA_FAIL);
        }
    }

    public List<UserDTO> getAll() {
        List<UserEntity> list = userRepository.findAll();
        List<UserEntity> listActive = new ArrayList<>();
        for (UserEntity u : list) {
            if (!u.isDeleted()) {
                listActive.add(u);
            }
        }
        if (listActive.isEmpty()) {
            throw new UserException(UserException.LIST_NOT_FOUND);
        }
        return userMapper.toListDto(listActive);
    }

    public String getGeneratedUsername(String fName, String lName) {
        String userName = fName.trim().toLowerCase();
        String afterStr = "";
        for (String s : lName.trim().split(" ")) {
            afterStr += s.charAt(0);
        }
        userName += afterStr;
        String lastIndex = "";
        String lastNumber = "";
        try{
            if (userRepository.findLastUsername(userName) != null) {
                String lastUsername = userRepository.findLastUsername(userName);
                char[] chars = lastUsername.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (Character.isDigit(chars[i])) {
                        lastNumber += String.valueOf(chars[i]);
                    }
                    if (chars[i] == chars[chars.length - 1] && !Character.isDigit(chars[i])) {
                        lastIndex += 1;
                        return userName.toLowerCase() + lastIndex;
                    }
                }
                lastIndex += Integer.parseInt(lastNumber) + 1;
            }
            return userName.toLowerCase() + lastIndex;
        } catch (Exception ex) {
            throw new RuntimeException(userName.toLowerCase() + lastIndex + userRepository.findLastUsername(userName));
        }

        //return userName.toLowerCase() + lastIndex;
    }

    public void deleteUser(String userCode) {
        UserEntity user = userRepository.findById(userCode).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        user.setDeleted(true);
        user.setStatus(UserEntity.EStatus.DISABLE);
        userRepository.save(user);
    }

    public UserDTO updateUser(UserEditDTO dto) {
        UserEntity user = userRepository.findById(dto.getUserCode()).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        switch (dto.getEditField()) {
            case "teacherSubject":
                if (user.getRoles().stream().anyMatch(role -> role.getRoleName().equalsIgnoreCase("TEACHER"))) {
                    SubjectEntity subject = subjectRepository.findBySubjectNameAndSubjectGrade(dto.getEditValue(), "1")
                            .orElseThrow(() -> new RuntimeException("Error: Subject is not found."));
                    List<SubjectEntity> list = new ArrayList<>();
                    list.add(subject);
                    user.setTeacherSubjects(list);
                } else {
                    throw new RuntimeException("Error: User is not a subject teacher.");
                }
                break;
            case "information":
                user.setFirstName(dto.getUserFName());
                user.setAddress(dto.getUserAddress());
                user.setLastName(dto.getUserLName());
                user.setDob(LocalDate.parse(dto.getDob()));
                user.setGender(dto.getGender());
                break;
            default:
                break;
        }
        userRepository.save(user);
        return userMapper.convertToDto(user);
    }

    public List<UserDTO> getUsersByClass(String className) {
        ClassEntity classEntity = classRepository.findByClassName(className).orElseThrow(() -> new RuntimeException("Not found class"));
        List<UserEntity> list = userRepository.findByStudentClass(classEntity);
        List<UserEntity> listActive = new ArrayList<>();
        for (UserEntity u : list) {
            if (!u.isDeleted()) {
                listActive.add(u);
            }
        }
        if (listActive.isEmpty()) {
            throw new UserException(UserException.LIST_NOT_FOUND);
        }
        return userMapper.toListDto(listActive);
    }

    public void addUserFromExcel(MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                List<UserDTO> list = ExcelHelper.excelToUsers(file.getInputStream());
                for (UserDTO user : list) {
                    createUser(user);
                }
            } catch (IOException exception) {
                throw new RuntimeException("fail to store excel data: " + exception.getMessage());
            }
        }
    }

}
