package com.my.contactbook.repository;

import com.my.contactbook.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);

    @Query(value = "select username\n" +
            " from user_db\n" +
            " where username like :username% \n" +
            " order by user_code DESC\n" +
            " LIMIT 1",
            nativeQuery = true)
    String findLastUsername(@Param("username") String username);
}
