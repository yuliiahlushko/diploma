package com.example.excursionPlanning.dao;

import com.example.excursionPlanning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> getUserById (@Param("id") Long id);
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> getUserByEmail(@Param("email") String email);



}
