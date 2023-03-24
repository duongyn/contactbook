package com.my.contactbook.repository;

import com.my.contactbook.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByCreatedBy(String createdBy);
}
