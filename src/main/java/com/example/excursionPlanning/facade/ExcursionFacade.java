package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.CommentDTO;
import com.example.excursionPlanning.dto.ExcursionDTO;
import com.example.excursionPlanning.entity.Comment;
import com.example.excursionPlanning.entity.Excursion;
import org.springframework.stereotype.Component;

@Component
public class ExcursionFacade {

    public static ExcursionDTO convertExcursionToExcursionDTO(Excursion excursion) {

        ExcursionDTO excursionDTO = new ExcursionDTO();
        excursionDTO.setId(excursion.getId());
        excursionDTO.setTitle(excursion.getTitle());
        excursionDTO.setDescription(excursion.getDescription());
        excursionDTO.setGuideId(excursion.getGuideId());
        excursionDTO.setPrice(excursion.getPrice());
        excursionDTO.setNumberOfSeats(excursion.getNumberOfSeats());
        excursionDTO.setStartTime(excursion.getStartTime());
        excursionDTO.setEndTime(excursion.getEndTime());
        excursionDTO.setLikes(excursion.getLikes());
        excursionDTO.setImage(excursion.getImage());
        excursionDTO.setMonuments(excursion.getMonuments());
        excursionDTO.setCreateDate(excursion.getCreateDate());

        return excursionDTO;
    }
}
