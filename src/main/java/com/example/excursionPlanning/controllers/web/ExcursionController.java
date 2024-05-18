package com.example.excursionPlanning.controllers.web;

import com.example.excursionPlanning.dto.ExcursionDTO;
import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.payload.web.ExcursionRequest;
import com.example.excursionPlanning.payload.web.MonumentRequest;
import com.example.excursionPlanning.services.interfaces.ExcursionService;
import com.example.excursionPlanning.services.interfaces.ImageModelService;
import com.example.excursionPlanning.services.interfaces.MonumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(value = "/web/excursions")
public class ExcursionController {

    @Autowired
    private MonumentService monumentService;

    @Autowired
    private ExcursionService excursionService;

    @Autowired
    private ImageModelService imageModelService;

    @GetMapping(value = "/new")
    public String getFormForCreateNewOne(Model model) {

        model.addAttribute("excursion", new ExcursionRequest());
        return "createExcursion";

    }

    @PostMapping(value = "/new")
    public String createMonument(@Valid @ModelAttribute("excursion") ExcursionRequest excursionRequest,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) {

        if (bindingResult.hasErrors()) {
            return "createExcursion";
        }

        try {
            excursionService.createExcursion(excursionRequest, principal)
                    .orElseThrow(() -> new RuntimeException("Excursion can't be created"));

        } catch (RuntimeException e) {

            model.addAttribute("error", e.getMessage());
            return "createExcursion";
        }
        return "redirect:/web/excursions";


    }

    @GetMapping()
    @ResponseBody
    public String getAllExcursion(Model model) {

        return "hello";
    }


}
