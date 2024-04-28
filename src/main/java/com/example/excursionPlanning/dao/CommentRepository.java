package com.example.excursionPlanning.dao;

import com.example.excursionPlanning.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.id = :id")
    Optional<Comment> getCommentById(@Param("id") Long id);

    @Query("SELECT c FROM Comment c WHERE c.monument.id = :monumentId ORDER BY c.createDate DESC")
    List<Comment> getAllCommentsByMonumentId(@Param("monumentId") Long monumentId);

    //Pageable
    @Query("SELECT c FROM Comment c WHERE c.monument.id = :monumentId ORDER BY c.createDate DESC")
    Page<Comment> getAllCommentsByMonumentId(@Param("monumentId") Long monumentId,
                                             Pageable pageable);

}
