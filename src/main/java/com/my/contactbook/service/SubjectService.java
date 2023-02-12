package com.my.contactbook.service;

import com.my.contactbook.dto.SubjectDTO;
import com.my.contactbook.mapper.SubjectMapper;
import com.my.contactbook.mapper.UserMapper;
import com.my.contactbook.repository.RoleRepository;
import com.my.contactbook.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectMapper subjectMapper;

    public SubjectDTO createSubject(SubjectDTO dto) {

        return subjectMapper.convertToDto(subjectRepository.save(subjectMapper.convertToEntity(dto)));
    }

}
