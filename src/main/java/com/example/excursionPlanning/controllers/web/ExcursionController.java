package com.example.excursionPlanning.controllers.web;

import com.example.excursionPlanning.dto.CommentDTO;
import com.example.excursionPlanning.dto.ExcursionDTO;
import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.facade.CommentFacade;
import com.example.excursionPlanning.facade.ExcursionFacade;
import com.example.excursionPlanning.facade.ImageModelFacadeResponse;
import com.example.excursionPlanning.facade.MonumentFacade;
import com.example.excursionPlanning.payload.web.ExcursionRequest;
import com.example.excursionPlanning.payload.web.ImageModelResponse;
import com.example.excursionPlanning.payload.web.MonumentRequest;
import com.example.excursionPlanning.payload.web.MonumentResponse;
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
import java.util.List;

@Controller
@RequestMapping(value = "/web/excursions")
public class ExcursionController {

    @Autowired
    private MonumentService monumentService;

    @Autowired
    private ExcursionService excursionService;

    @Autowired
    private ImageModelService imageModelService;

    @Autowired
    private ExcursionFacade excursionFacade;

    @Autowired
    private MonumentFacade monumentFacade;

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

    @PostMapping(value = "/{id}/like")
    public String likeExcursion(Principal principal,
                                @PathVariable String id,
                                Model model) {

        try {
            excursionService.like(Long.parseLong(id), principal);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/web/excursions";
        }
        return "redirect:/web/excursions";

    }

    @GetMapping()
    @ResponseBody
    public String getAllExcursion(Model model) {

        return "hello";
    }

    @PatchMapping()
    public String updateMonumentInfo(@Valid @ModelAttribute("excursion") ExcursionDTO excursion,
                                     BindingResult bindingResult,
                                     Principal principal, Model model) {

        if (bindingResult.hasErrors()) {
            return "editExcursion";
        }

        ExcursionDTO savedExcursion = null;
        try {
            savedExcursion = ExcursionFacade.convertExcursionToExcursionDTO(excursionService.patchExcursion(excursion, principal)
                    .orElseThrow(() -> new RuntimeException("Excursion not updated")));

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("excursion", excursion);
            return "editExcursion";
        }

        model.addAttribute("excursion", savedExcursion);
        return "excursionPage";

    }

    @GetMapping(value = "/{id}/edit")
    public String editMonumentById(@PathVariable("id") String id,
                                   Principal principal,
                                   Model model) {
        ExcursionDTO excursion = null;
        try {
            excursion = ExcursionFacade
                    .convertExcursionToExcursionDTO(excursionService
                            .getExcursionById(Long.parseLong(id), principal)
                            .orElseThrow(() -> new RuntimeException("Excursion not updated")));

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("excursion", excursion);
        return "editExcursion";

    }

    @DeleteMapping("/{id}")
    public String DeleteMonumentById(@PathVariable("id") String id, Principal principal) {

        excursionService.deleteExcursion(Long.parseLong(id), principal);

        return "redirect:/web/excursions";
    }

    @GetMapping(value = "/{id}")
    public String getMonumentById(@PathVariable("id") String id,
                                  Principal principal,
                                  Model model) {
        ExcursionDTO excursionDTO = null;

        try {
            excursionDTO = excursionFacade
                    .convertExcursionToExcursionDTO(excursionService
                            .getExcursionById(Long.parseLong(id), principal)
                            .orElseThrow(() -> new RuntimeException("Monument not found")));

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("excursion", excursionDTO);


        try {

            List<MonumentDTO> monuments = monumentService
                    .getMonumentsByExcursionId(Long.parseLong(id))
                    .stream()
                    .map(monumentFacade::convertMonumentToMonumentDTO)
                    .toList();


            model.addAttribute("monuments", monuments);


        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }

        return "excursionPage";


    }


}
