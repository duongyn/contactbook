package com.my.contactbook.repository;

import com.my.contactbook.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    Optional<SubjectEntity> findBySubjectName(String subjectName);
}
