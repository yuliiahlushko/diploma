package com.example.excursionPlanning.dao;


import com.example.excursionPlanning.entity.Grade;
import com.example.excursionPlanning.entity.ImageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ImageModelRepository extends JpaRepository<ImageModel, Long> {

    @Query("SELECT im FROM ImageModel im WHERE im.id = :id")
    Optional<ImageModel> getImageModelById(@Param("id") Long id);
    @Query("SELECT im FROM ImageModel im WHERE im.monumentId = :monumentId ")
    List<ImageModel> getAllImageModelsByMonumentId(@Param("monumentId") Long monumentId);

    //Pageable
    @Query("SELECT im FROM ImageModel im WHERE im.monumentId = :monumentId ")
    Page<ImageModel> getAllImageModelsByMonumentId(@Param("monumentId") Long monumentId,
                                                   Pageable pageable);
}
