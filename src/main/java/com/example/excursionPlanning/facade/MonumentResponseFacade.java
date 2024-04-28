package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.payload.web.MonumentRequest;
import com.example.excursionPlanning.payload.web.MonumentResponse;
import org.springframework.stereotype.Component;

@Component
public class MonumentResponseFacade {

    public MonumentDTO convertMonumentResponseToMonumentDTO(MonumentResponse monument) {

        MonumentDTO monumentDTO = new MonumentDTO();

        monumentDTO.setTitle(monument.getTitle());
        monumentDTO.setPrice(monument.getPrice());
        monumentDTO.setDescription(monument.getDescription());
        monumentDTO.setCity(monument.getCity());
        monumentDTO.setId(monument.getId());

        return monumentDTO;


    }

}
