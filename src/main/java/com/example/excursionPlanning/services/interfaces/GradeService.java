package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.dto.ExceptionDTO;
import com.example.excursionPlanning.dto.GradeDTO;
import com.example.excursionPlanning.entity.Comment;
import com.example.excursionPlanning.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public interface GradeService {

    Grade createGrade(GradeDTO gradeDTO, Principal principal);

    void deleteGrade(Long id, Principal principal);

    Optional<Grade> getGradeById(Long id, Principal principal);

    Grade putGrade(GradeDTO gradeDTO, Principal principal);

    Grade patchGrade(GradeDTO gradeDTO, Principal principal);

    Optional<Comment> getAllGradesByMonumentId(Long monumentId);

    //Pageable
    Page<Comment> getAllGradesByMonumentId(Long monumentId, Pageable pageable);
}
