package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.dto.ExceptionDTO;
import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.Monument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public interface MonumentService {

    Monument createMonument(MonumentDTO monumentDTO, Principal principal);

    void deleteMonument(Long id, Principal principal);

    Optional<Monument> getMonumentById(Long id, Principal principal);

    Monument putMonument(MonumentDTO monumentDTO, Principal principal);

    Monument patchMonument(MonumentDTO monumentDTO, Principal principal);


    List<Monument> getMonumentByTitle(String title);

    List<Monument> getMonumentsByPrice(Long minPrice, Long maxPrice);

    List<Monument> getMonumentsByAvgGrade(Integer avgGrade);

    List<Monument> getMonumentsByExcursionId(Long excursionId);

    //Pageable

    Page<Monument> getMonumentByTitle(String title, Pageable pageable);

    Page<Monument> getMonumentsByPrice(Long minPrice, Long maxPrice, Pageable pageable);

    Page<Monument> getMonumentsByAvgGrade(Integer avgGrade, Pageable pageable);

    Page<Monument> getMonumentsByExcursionId(Long excursionId, Pageable pageable);
}
