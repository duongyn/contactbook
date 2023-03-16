package com.my.contactbook.service;

import com.my.contactbook.dto.SubjectDTO;
import com.my.contactbook.entity.SubjectEntity;
import com.my.contactbook.mapper.SubjectMapper;
import com.my.contactbook.mapper.UserMapper;
import com.my.contactbook.repository.RoleRepository;
import com.my.contactbook.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectMapper subjectMapper;

    public SubjectDTO createSubject(SubjectDTO dto) {
        if(subjectRepository.existsBySubjectNameAndSubjectGrade(dto.getSubjectName(), dto.getSubjectGrade())){
            throw new RuntimeException("Error: The subject exists in database");
        }
        try {
            int grade = Integer.parseInt(dto.getSubjectGrade());
            if(grade > 0 && grade <= 5) {
                SubjectEntity entity = subjectMapper.convertToEntity(dto);
                entity.setDeleted(false);
                return subjectMapper.convertToDto(subjectRepository.save(entity));
            }
            else {
                throw new RuntimeException("Error: Grade must be from 1 to 5");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error: Grade format is wrong");
        }
    }

    public SubjectDTO editSubject(SubjectDTO dto) {
        SubjectEntity entity = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Error: Not found subject"));
        entity.setSubjectName(dto.getSubjectName());
        try {
            int grade = Integer.parseInt(dto.getSubjectGrade());
            if(grade > 0 && grade <= 5) {
                entity.setSubjectGrade(dto.getSubjectGrade());
            }
            else {
                throw new RuntimeException("Error: Grade must be from 1 to 5");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error: Grade format is wrong");
        }
        return subjectMapper.convertToDto(subjectRepository.save(entity));
    }

    public void deleteSubject(long subjectId) {
        SubjectEntity entity = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Error: Not found subject"));
        entity.setDeleted(true);
        subjectRepository.save(entity);
    }

    public List<SubjectDTO> getAll(){
        List<SubjectEntity> list = subjectRepository.findAll();
        List<SubjectEntity> listActive = new ArrayList<>();
        for(SubjectEntity s: list){
            if(!s.isDeleted()){
                listActive.add(s);
            }
        }
        return subjectMapper.toListDto(listActive);
    }

    public SubjectDTO getById(long id){
        return subjectMapper.convertToDto(subjectRepository.findById(id).orElse(null));
    }
}
