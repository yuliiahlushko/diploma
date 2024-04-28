package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.Monument;
import org.springframework.stereotype.Component;


@Component
public class MonumentFacade {

    public MonumentDTO convertMonumentToMonumentDTO(Monument monument) {

        MonumentDTO monumentDTO = new MonumentDTO();

        monumentDTO.setTitle(monument.getTitle());
        monumentDTO.setPrice(monument.getPrice());
        monumentDTO.setDescription(monument.getDescription());
        monumentDTO.setAvgGrade(monument.getAvgGrade());
        monumentDTO.setCreatedDate(monument.getCreatedDate());
        monumentDTO.setCity(monument.getCity());
        monumentDTO.setId(monument.getId());

        return monumentDTO;


    }

}
