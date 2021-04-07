package com.secury.repo;

import com.secury.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT r from User r WHERE fullname LIKE %:keyword% ")
    List<User> findByDes(@Param("keyword")String name);

    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update users set password = :password where id = :id", nativeQuery = true)
    void updatepassword(@Param("password")String password, @Param("id")int id);


}
