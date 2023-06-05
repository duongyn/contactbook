package com.my.contactbook.repository;

import com.my.contactbook.entity.MarkEntity;
import com.my.contactbook.entity.SubjectEntity;
import com.my.contactbook.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<MarkEntity, Long> {
    List<MarkEntity> findByUserId(UserEntity userId);

    Optional<MarkEntity> findByUserIdAndSubjectIdAndSemester(UserEntity userId, SubjectEntity subjectId, String semester);
}
