package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.ImageModel;
import com.example.excursionPlanning.entity.Monument;
import com.example.excursionPlanning.payload.web.MonumentExcursionResponse;
import com.example.excursionPlanning.payload.web.MonumentResponse;
import com.example.excursionPlanning.services.interfaces.ImageModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class MonumentExcursionResponseFacade {

    private ImageModelService imageModelService;

    @Autowired
    public MonumentExcursionResponseFacade(ImageModelService imageModelService) {
        this.imageModelService = imageModelService;
    }

    public MonumentExcursionResponse convertMonumentDTOToMonumentExcursionResponse(Monument monument) {

        MonumentExcursionResponse monumentExcursionResponse = new MonumentExcursionResponse();

        monumentExcursionResponse.setTitle(monument.getTitle());
        monumentExcursionResponse.setPrice(monument.getPrice());
        monumentExcursionResponse.setDescription(monument.getDescription());
        monumentExcursionResponse.setCity(monument.getCity());
        monumentExcursionResponse.setId(monument.getId());


        List<ImageModel> imageModels = imageModelService
                .getAllImageModelsByMonumentId(monument.getId());

        ImageModel imageModel = imageModels.get(0);

        if (imageModels.size()>1) imageModel = imageModels.get(1);

        String image = Base64.getEncoder().encodeToString(imageModel.getImageBytes());

        monumentExcursionResponse.setImage(image);

        return monumentExcursionResponse;


    }
}
