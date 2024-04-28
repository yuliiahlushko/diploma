package com.example.excursionPlanning.dao;


import com.example.excursionPlanning.entity.Excursion;
import com.example.excursionPlanning.entity.Monument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonumentRepository extends JpaRepository<Monument, Long> {

    @Query("SELECT m FROM Monument m WHERE m.id = :id")
    Optional<Monument> getMonumentById(@Param("id") Long id);

    @Query("SELECT m FROM Monument m order by m.avgGrade DESC")
    List<Monument> getAllMonuments();

    @Query("SELECT m FROM Monument m WHERE m.title like :title order by m.avgGrade DESC")
    List<Monument> getMonumentByTitle(@Param("title") String title);

    @Query("SELECT m FROM Monument m WHERE m.price <= :maxPrice AND m.price >= :minPrice ")
    List<Monument> getMonumentsByPrice(@Param("minPrice") Long minPrice,
                                       @Param("maxPrice") Long maxPrice);
    @Query("SELECT m FROM Monument m WHERE m.city like :city order by m.avgGrade DESC")
    List<Monument> getMonumentsByCity(@Param("city") String city);
    @Query("SELECT m FROM Monument m WHERE m.avgGrade >= :avgGrade order by m.avgGrade DESC")
    List<Monument> getMonumentsByAvgGrade(@Param("avgGrade") Integer avgGrade);

    @Query("SELECT m FROM Monument m LEFT OUTER JOIN Excursion e " +
            "WHERE e.id = :excursionId")
    List<Monument> getMonumentsByExcursionId(@Param("excursionId") Long excursionId);

    //Pageable

    @Query("SELECT m FROM Monument m order by m.avgGrade DESC")
    Page<Monument> getAllMonuments(Pageable pageable);

    @Query("SELECT m FROM Monument m WHERE m.title like :title order by m.avgGrade DESC")
    Page<Monument> getMonumentByTitle(@Param("title") String title,
                                      Pageable pageable);

    @Query("SELECT m FROM Monument m WHERE m.city like :city order by m.avgGrade DESC")
    Page<Monument> getMonumentsByCity(@Param("city") String city,
                                      Pageable pageable);

    @Query("SELECT m FROM Monument m WHERE m.price <= :maxPrice AND m.price >= :minPrice ")
    Page<Monument> getMonumentsByPrice(@Param("minPrice") Long minPrice,
                                       @Param("maxPrice") Long maxPrice,
                                       Pageable pageable);

    @Query("SELECT m FROM Monument m WHERE m.avgGrade >= :avgGrade order by m.avgGrade DESC")
    Page<Monument> getMonumentsByAvgGrade(@Param("avgGrade") Integer avgGrade,
                                          Pageable pageable);

    @Query("SELECT m FROM Monument m LEFT OUTER JOIN Excursion e " +
            "WHERE e.id = :excursionId")
    Page<Monument> getMonumentsByExcursionId(@Param("excursionId") Long excursionId,
                                             Pageable pageable);

}
