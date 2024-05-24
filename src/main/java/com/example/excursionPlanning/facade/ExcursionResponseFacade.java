package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.ExcursionDTO;
import com.example.excursionPlanning.payload.web.ExcursionResponse;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ExcursionResponseFacade {
    public static ExcursionResponse convertExcursionDTOToExcursionResponse(ExcursionDTO excursionDTO) {

        ExcursionResponse excursion = new ExcursionResponse();
        excursion.setId(excursionDTO.getId());
        excursion.setTitle(excursionDTO.getTitle());
        excursion.setDescription(excursionDTO.getDescription());
        excursion.setGuideId(excursionDTO.getGuideId());
        excursion.setPrice(excursionDTO.getPrice());
        excursion.setNumberOfSeats(excursionDTO.getNumberOfSeats());
        excursion.setStartTime(excursionDTO.getStartTime());
        excursion.setEndTime(excursionDTO.getEndTime());
        excursion.setLikes(excursionDTO.getLikes());
        excursion.setImage(Base64.getEncoder().encodeToString(excursionDTO.getImage()));
        excursion.setMonuments(excursionDTO.getMonuments());
        excursion.setCreateDate(excursionDTO.getCreateDate());

        return excursion;
    }

}
