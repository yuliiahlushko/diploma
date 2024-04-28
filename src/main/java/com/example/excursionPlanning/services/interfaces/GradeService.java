package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.dto.GradeDTO;
import com.example.excursionPlanning.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public interface GradeService {

    Grade createGrade(GradeDTO gradeDTO, Principal principal);

    void deleteGrade(Long id, Principal principal);

    Optional<Grade> getGradeById(Long id, Principal principal);

    Optional<Grade> putGrade(GradeDTO gradeDTO, Principal principal);

    Optional<Grade> patchGrade(GradeDTO gradeDTO, Principal principal);

    List<Grade> getAllGradesByMonumentId(Long monumentId);

    //Pageable
    Page<Grade> getAllGradesByMonumentId(Long monumentId, Pageable pageable);
}
