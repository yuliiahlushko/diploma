package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.dto.ExceptionDTO;
import com.example.excursionPlanning.entity.Excursion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ExcursionService {


    Exception createException(ExceptionDTO exceptionDTO, Principal principal);

    void deleteException(Long id, Principal principal);

    Optional<Exception> getExceptionById(Long id, Principal principal);

    Exception putException(ExceptionDTO exceptionDTO, Principal principal);

    Exception patchException(ExceptionDTO exceptionDTO, Principal principal);




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
