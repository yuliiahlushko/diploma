package com.example.excursionPlanning.dao;

import com.example.excursionPlanning.entity.Excursion;
import com.example.excursionPlanning.entity.Monument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    @Query("SELECT e FROM Excursion e WHERE e.guideId = :guideId ORDER BY e.likes DESC")
    List<Excursion> getAllConductedExcursionsByUser(@Param("guideId") Long guideId);

    @Query("SELECT e FROM Excursion e WHERE e.id = :id")
    Optional<Excursion> getExcursionById(@Param("id") Long id);

    @Query("SELECT e FROM Excursion e WHERE e.title like :title ORDER BY e.likes DESC")
    List<Excursion> getExcursionsByTitle(@Param("title") String title);

    @Query("SELECT e FROM Excursion e WHERE e.price <= :maxPrice AND e.price >= :minPrice ORDER BY e.likes DESC")
    List<Excursion> getExcursionsByPrice(@Param("minPrice") Long minPrice,
                                         @Param("maxPrice") Long maxPrice);

    @Query("SELECT e FROM Excursion e WHERE e.startTime >= :startTime AND e.endTime <= :endTime ORDER BY e.createDate DESC")
    List<Excursion> getExcursionsByTime(@Param("startTime") LocalDateTime startTime,
                                        @Param("endTime") LocalDateTime endTime);

    @Query("SELECT e FROM Excursion e WHERE e.createDate >= :time ORDER BY e.likes DESC")
    List<Excursion> getExcursionsCreatedAfterTime(@Param("time") LocalDateTime time);

    @Query("SELECT e FROM Excursion e WHERE e.createDate <= :time ORDER BY e.likes DESC")
    List<Excursion> getExcursionsCreatedBeforeTime(@Param("time") LocalDateTime time);

    @Query("SELECT e FROM Excursion e LEFT OUTER JOIN Monument m " +
            "WHERE m.id = :monumentId")
    List<Excursion> getExcursionByMonumentId(@Param("monumentId") Long monumentId);

    //Pageable

    @Query("SELECT e FROM Excursion e WHERE e.guideId = :guideId ORDER BY e.likes DESC")
    Page<Excursion> getAllConductedExcursionsByUser(@Param("guideId") Long guideId,
                                                    Pageable pageable);

    @Query("SELECT e FROM Excursion e WHERE e.title like :title ORDER BY e.likes DESC")
    Page<Excursion> getExcursionsByTitle(@Param("title") String title,
                                         Pageable pageable);

    @Query("SELECT e FROM Excursion e WHERE e.price <= :maxPrice AND e.price >= :minPrice ORDER BY e.likes DESC")
    Page<Excursion> getExcursionsByPrice(@Param("minPrice") Long minPrice,
                                         @Param("maxPrice") Long maxPrice,
                                         Pageable pageable);

    @Query("SELECT e FROM Excursion e WHERE e.startTime >= :startTime AND e.endTime <= :endTime ORDER BY e.createDate DESC")
    Page<Excursion> getExcursionsByTime(@Param("startTime") LocalDateTime startTime,
                                        @Param("endTime") LocalDateTime endTime,
                                        Pageable pageable);

    @Query("SELECT e FROM Excursion e WHERE e.createDate >= :time ORDER BY e.likes DESC")
    Page<Excursion> getExcursionsCreatedAfterTime(@Param("time") LocalDateTime time,
                                                  Pageable pageable);

    @Query("SELECT e FROM Excursion e WHERE e.createDate <= :time ORDER BY e.likes DESC")
    Page<Excursion> getExcursionsCreatedBeforeTime(@Param("time") LocalDateTime time,
                                                   Pageable pageable);

    @Query("SELECT e FROM Excursion e LEFT OUTER JOIN Monument m " +
            "WHERE m.id = :monumentId")
    Page<Excursion> getExcursionByMonumentId(@Param("monumentId") Long monumentId,
                                             Pageable pageable);
}
