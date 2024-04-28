package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.Monument;
import com.example.excursionPlanning.paginationandsorting.PageSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public interface MonumentService {

    Optional<Monument> createMonument(MonumentDTO monumentDTO, Principal principal);

    void deleteMonument(Long id, Principal principal);

    Optional<Monument> getMonumentById(Long id, Principal principal);

    Optional<Monument> putMonument(MonumentDTO monumentDTO, Principal principal);

    Optional<Monument> patchMonument(MonumentDTO monumentDTO, Principal principal);


    List<Monument> getAllMonuments();

    List<Monument> getMonumentsByCity(String city);

    List<Monument> getMonumentByTitle(String title);

    List<Monument> getMonumentsByPrice(Long minPrice, Long maxPrice);

    List<Monument> getMonumentsByAvgGrade(Integer avgGrade);

    List<Monument> getMonumentsByExcursionId(Long excursionId);

    //Pageable

    List<Monument> getAllMonuments(PageSettings pageSetting);

    List<Monument> getMonumentsByCity(String city,PageSettings pageSetting);

    List<Monument> getMonumentsByTitle(String title, PageSettings pageSetting);

    List<Monument> getMonumentsByPrice(Long minPrice, Long maxPrice, PageSettings pageSetting);

    List<Monument> getMonumentsByAvgGrade(Integer avgGrade, PageSettings pageSetting);

    List<Monument> getMonumentsByExcursionId(Long excursionId, PageSettings pageSetting);
}
