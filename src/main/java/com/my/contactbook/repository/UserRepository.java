package com.my.contactbook.repository;

import com.my.contactbook.entity.ClassEntity;
import com.my.contactbook.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);

    @Query(value = "select username\n" +
            " from user_db\n" +
            " where username like :username% \n" +
            " order by username DESC\n" +
            " LIMIT 1",
            nativeQuery = true)
    String findLastUsername(@Param("username") String username);

    List<UserEntity> findByStudentClass(ClassEntity studentClass);

    @Query(value = "select EXISTS(\n" +
            " select * from user_db\n" +
            " where user_code = :userCode and fname = :firstName and lname = :lastName \n" +
            " )",
            nativeQuery = true)
    int checkValidUser(@Param("userCode") String userCode, @Param("firstName") String firstName, @Param("lastName") String lastName);
}
