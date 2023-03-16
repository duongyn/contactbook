package com.my.contactbook.repository;

import com.my.contactbook.entity.MarkEntity;
import com.my.contactbook.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<MarkEntity, Long> {
    List<MarkEntity> findByUserId(UserEntity userId);
}
