package com.example.excursionPlanning.dao;

import com.example.excursionPlanning.entity.Comment;
import com.example.excursionPlanning.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {


    @Query("SELECT g FROM Grade g WHERE g.monument.id = :monumentId ORDER BY g.createDate DESC")
    Optional<Comment> getAllGradesByMonumentId(@Param("monumentId") Long monumentId);

    //Pageable
    @Query("SELECT g FROM Grade g WHERE g.monument.id = :monumentId ORDER BY g.createDate DESC")
    Page<Comment> getAllGradesByMonumentId(@Param("monumentId") Long monumentId,
                                           Pageable pageable);
}