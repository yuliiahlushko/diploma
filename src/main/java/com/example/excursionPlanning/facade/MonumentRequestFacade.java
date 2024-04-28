package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.Monument;
import com.example.excursionPlanning.payload.web.MonumentRequest;
import org.springframework.stereotype.Component;

@Component
public class MonumentRequestFacade {

    public MonumentDTO convertMonumentRequestToMonumentDTO(MonumentRequest monument) {

        MonumentDTO monumentDTO = new MonumentDTO();

        monumentDTO.setTitle(monument.getTitle());
        monumentDTO.setPrice(monument.getPrice());
        monumentDTO.setDescription(monument.getDescription());
        monumentDTO.setCity(monument.getCity());

        return monumentDTO;


    }

}
