package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.ExcursionDTO;
import com.example.excursionPlanning.entity.Excursion;
import com.example.excursionPlanning.payload.web.ExcursionsResponse;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ExcursionsResponseFacade {
    public ExcursionsResponse convertExcursionToExcursionsResponse(Excursion excursionDTO) {

        ExcursionsResponse excursion = new ExcursionsResponse();
        excursion.setId(excursionDTO.getId());
        excursion.setTitle(excursionDTO.getTitle());
        excursion.setPrice(excursionDTO.getPrice());
        excursion.setStartTime(excursionDTO.getStartTime());
        excursion.setEndTime(excursionDTO.getEndTime());
        excursion.setLikes(excursionDTO.getLikes());
        excursion.setImage(Base64.getEncoder().encodeToString(excursionDTO.getImage()));


        return excursion;
    }
}
