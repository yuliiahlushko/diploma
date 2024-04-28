package com.example.excursionPlanning.services.implementations;

import com.example.excursionPlanning.dao.GradeRepository;
import com.example.excursionPlanning.dto.GradeDTO;
import com.example.excursionPlanning.entity.Grade;
import com.example.excursionPlanning.services.interfaces.GradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradeServiceImpl implements GradeService {

    private static final Logger LOG = LoggerFactory.getLogger(GradeServiceImpl.class);

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public Grade createGrade(GradeDTO gradeDTO, Principal principal) {
        Grade grade = new Grade();

        grade.setGrade(gradeDTO.getGrade());
        grade.setMonument(gradeDTO.getMonument());
        grade.setUserId(gradeDTO.getUserId());
        grade.setLogin(gradeDTO.getLogin());

        Grade savedGrade = null;

        try {
            savedGrade = gradeRepository.save(grade);
        } catch (Exception e) {
            LOG.error("Grade {} cannot be added!", e.getMessage());
        }
        LOG.info("New grade {} was added", savedGrade);
        return savedGrade;
    }

    @Override
    public void deleteGrade(Long id, Principal principal) {

        Grade grade = gradeRepository.getGradeById(id)
                .orElseThrow(() -> new RuntimeException("Grade not exist"));
        try {
            gradeRepository.delete(grade);
        } catch (Exception e) {
            LOG.error("Grade {} cannot be deleted!", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Grade> getGradeById(Long id, Principal principal) {
        return gradeRepository.getGradeById(id);
    }

    @Override
    public Optional<Grade> putGrade(GradeDTO gradeDTO, Principal principal) {
        Optional<Grade> grade = gradeRepository.getGradeById(gradeDTO.getId());

        if (grade.isPresent()) {

            grade.get().setGrade(gradeDTO.getGrade());
            grade.get().setMonument(gradeDTO.getMonument());
            grade.get().setUserId(gradeDTO.getUserId());
            grade.get().setLogin(gradeDTO.getLogin());

            Grade savedGrade = null;
            try {
                savedGrade = gradeRepository.save(grade.get());
                return Optional.of(savedGrade);
            } catch (Exception e) {
                LOG.error("Grade {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    public Optional<Grade> patchGrade(GradeDTO gradeDTO, Principal principal) {
        Optional<Grade> grade = gradeRepository.getGradeById(gradeDTO.getId());

        if (grade.isPresent()) {

            if (gradeDTO.getGrade() != null)
                grade.get().setGrade(gradeDTO.getGrade());
            if (gradeDTO.getMonument() != null)
                grade.get().setMonument(gradeDTO.getMonument());
            if (gradeDTO.getUserId() != null)
                grade.get().setUserId(gradeDTO.getUserId());
            if (gradeDTO.getLogin() != null)
                grade.get().setLogin(gradeDTO.getLogin());

            Grade savedGrade = null;
            try {
                savedGrade = gradeRepository.save(grade.get());
                return Optional.of(savedGrade);
            } catch (Exception e) {
                LOG.error("Grade {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grade> getAllGradesByMonumentId(Long monumentId) {
        return gradeRepository.getAllGradesByMonumentId(monumentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Grade> getAllGradesByMonumentId(Long monumentId, Pageable pageable) {
        return gradeRepository.getAllGradesByMonumentId(monumentId, pageable);
    }
}
