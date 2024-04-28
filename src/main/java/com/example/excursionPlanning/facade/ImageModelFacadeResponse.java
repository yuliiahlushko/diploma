package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.entity.ImageModel;
import com.example.excursionPlanning.payload.web.ImageModelResponse;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ImageModelFacadeResponse {

    public static ImageModelResponse convertImageModelToImageModelResponse(ImageModel imageModel) {

        ImageModelResponse imageModelResponse = new ImageModelResponse();

        imageModelResponse.setId(imageModel.getId());
        imageModelResponse.setImage(Base64.getEncoder().encodeToString(imageModel.getImageBytes()));
        return imageModelResponse;
    }
}
