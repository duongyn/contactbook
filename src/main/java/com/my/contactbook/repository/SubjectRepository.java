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

    @Query(value = "SELECT * FROM contactbook.subject s inner join\n" +
            "            (select c.class_name, cs.subject_id from class_db c\n" +
            "            inner join class_subject cs\n" +
            "            on c.class_id = cs.class_id\n" +
            "            where c.class_name = :classname) as cts_tbl\n" +
            "            on cts_tbl.subject_id = s.subject_id;",
            nativeQuery = true)
    List<SubjectEntity> findByClassName(@Param("classname") String className);

    Boolean existsBySubjectNameAndSubjectGrade(String subjectName, String subjectGrade);

}
