package com.my.contactbook.service;

import com.my.contactbook.dto.MarkDTO;
import com.my.contactbook.entity.MarkEntity;
import com.my.contactbook.entity.SubjectEntity;
import com.my.contactbook.entity.UserEntity;
import com.my.contactbook.mapper.MarkMapper;
import com.my.contactbook.repository.MarkRepository;
import com.my.contactbook.repository.SubjectRepository;
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
public class MarkService {
    private static final Logger logger = LoggerFactory.getLogger(MarkService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private MarkMapper markMapper;

    public MarkDTO createMark(MarkDTO dto) {
        MarkEntity entity = markMapper.convertToEntity(dto);
        UserEntity student = userRepository.findById(dto.getStudentCode())
                .orElseThrow(() -> new RuntimeException("Not found student with code: " + dto.getStudentCode()));
        entity.setUserId(student);
        entity.setCreatedBy(dto.getTeacherCode());
        SubjectEntity subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Not found subject with id: " + dto.getSubjectId()));
        entity.setSubjectId(subject);
        return markMapper.convertToDto(markRepository.save(entity));
    }

    public List<MarkDTO> findMarksByStudent(String studentCode) {
        UserEntity student = userRepository.findById(studentCode)
                .orElseThrow(() -> new RuntimeException("Not found student with code: " + studentCode));
        List<MarkEntity> markList = markRepository.findByUserId(student);
        List<MarkDTO> dtoList = new ArrayList<>();
        if (markList.size() > 0) {
            dtoList = markMapper.toListDto(markList);
        }
        return dtoList;
    }

}
