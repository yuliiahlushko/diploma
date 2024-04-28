package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.dto.ImageModelDTO;
import com.example.excursionPlanning.entity.ImageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public interface ImageModelService {

    ImageModel createImageModel(ImageModelDTO imageModelDTO, Principal principal);

    void deleteImageModel(Long id, Principal principal);

    Optional<ImageModel> getImageModelById(Long id, Principal principal);

    Optional<ImageModel> putImageModel(ImageModelDTO imageModelDTO, Principal principal);

    Optional<ImageModel> patchImageModel(ImageModelDTO imageModelDTO, Principal principal);

    List<ImageModel> getAllImageModelsByMonumentId(Long monumentId);

    //Pageable

    Page<ImageModel> getAllImageModelsByMonumentId(Long monumentId, Pageable pageable);

}
