package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.dto.ExcursionDTO;
import com.example.excursionPlanning.entity.Excursion;
import com.example.excursionPlanning.payload.web.ExcursionEditRequest;
import com.example.excursionPlanning.payload.web.ExcursionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ExcursionService {


    Optional<Excursion> createExcursion(ExcursionRequest excursion, Principal principal);

    void deleteExcursion(Long id, Principal principal);

    void like(Long id, Principal principal);

    public void getTicket(Long id, Principal principal);

    Optional<Excursion> getExcursionById(Long id);

    Optional<Excursion> putExcursion(ExcursionDTO excursionDTO, Principal principal);

    public Optional<Excursion> deletePhoto(Long excursionId, Principal principal);

    Optional<Excursion> patchExcursion(ExcursionEditRequest excursion, Principal principal);


    List<Excursion> getAllConductedExcursionsByUser(Long guideId);

    List<Excursion> getExcursionsByTitle(String title);

    List<Excursion> getExcursionsByPrice(Long minPrice, Long maxPrice);

    List<Excursion> getExcursionsByTime(LocalDateTime startTime, LocalDateTime endTime);

    List<Excursion> getExcursionsCreatedAfterTime(LocalDateTime time);

    List<Excursion> getExcursionsCreatedBeforeTime(LocalDateTime time);

    List<Excursion> getExcursionByMonumentId(Long monumentId);

    //Pageable

    Page<Excursion> getAllConductedExcursionsByUser(Long guideId, Pageable pageable);

    Page<Excursion> getExcursionsByTitle(String title, Pageable pageable);

    Page<Excursion> getExcursionsByPrice(Long minPrice, Long maxPrice, Pageable pageable);

    Page<Excursion> getExcursionsByTime(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    Page<Excursion> getExcursionsCreatedAfterTime(LocalDateTime time, Pageable pageable);

    Page<Excursion> getExcursionsCreatedBeforeTime(LocalDateTime time, Pageable pageable);

    Page<Excursion> getExcursionByMonumentId(Long monumentId, Pageable pageable);
}
