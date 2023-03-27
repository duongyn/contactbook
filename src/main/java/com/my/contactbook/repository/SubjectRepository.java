package com.my.contactbook.repository;

import com.my.contactbook.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    Optional<SubjectEntity> findBySubjectNameAndSubjectGrade(String subjectName, String subjectGrade);

    List<SubjectEntity> findBySubjectGrade(String subjectGrade);

    @Query(value = "SELECT * FROM contactbook.subject s inner join \n" +
            "(select c.class_name, ts.subject_id from class_db c \n" +
            "inner join teacher_subject ts \n" +
            "on c.form_teacher = ts.user_code) as cts_tbl\n" +
            "on cts_tbl.subject_id = s.subject_id\n" +
            "where cts_tbl.class_name = :classname",
            nativeQuery = true)
    List<SubjectEntity> findByClassName(@Param("classname") String className);

    Boolean existsBySubjectNameAndSubjectGrade(String subjectName, String subjectGrade);

}
