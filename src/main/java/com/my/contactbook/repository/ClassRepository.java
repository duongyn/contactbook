package com.my.contactbook.repository;

import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    Optional<ClassEntity> findByClassName(String className);

    Boolean existsByFormTeacher(UserEntity formTeacher);
}
