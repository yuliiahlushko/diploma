package com.example.excursionPlanning.controllers.web;

import com.example.excursionPlanning.dto.ExcursionDTO;
import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.Excursion;
import com.example.excursionPlanning.entity.Monument;
import com.example.excursionPlanning.facade.*;
import com.example.excursionPlanning.payload.web.*;
import com.example.excursionPlanning.services.interfaces.ExcursionService;
import com.example.excursionPlanning.services.interfaces.ImageModelService;
import com.example.excursionPlanning.services.interfaces.MonumentService;
import com.example.excursionPlanning.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/web/excursions")
public class ExcursionController {

    @Autowired
    private MonumentService monumentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExcursionService excursionService;

    @Autowired
    private ImageModelService imageModelService;

    @Autowired
    private ExcursionFacade excursionFacade;

    @Autowired
    private MonumentFacade monumentFacade;

    @Autowired
    private ExcursionResponseFacade excursionResponseFacade;

    @Autowired
    private MonumentExcursionResponseFacade monumentExcursionResponseFacade;

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
        Excursion excursion = null;
        try {
            excursion = excursionService.createExcursion(excursionRequest, principal)
                    .orElseThrow(() -> new RuntimeException("Excursion can't be created"));

        } catch (RuntimeException e) {

            model.addAttribute("error", e.getMessage());
            return "createExcursion";
        }
        return "redirect:/web/excursions/" + excursion.getId();


    }

    @GetMapping(value = "/{id}/like")
    public String likeExcursion(Principal principal,
                                @PathVariable String id,
                                Model model) {

        try {
            excursionService.like(Long.parseLong(id), principal);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }
        return "redirect:/web/excursions/" + id;

    }

    @GetMapping()
    @ResponseBody
    public String getAllExcursion(Model model) {

        return "hello";
    }

    @PatchMapping()
    public String updateExcursionInfo(@Valid @ModelAttribute("excursion") ExcursionEditRequest excursion,
                                      BindingResult bindingResult,
                                      Principal principal, Model model) {


        if (bindingResult.hasErrors()) {
            return "editExcursion";
        }

        ExcursionDTO savedExcursion = null;


        try {
            savedExcursion = ExcursionFacade
                    .convertExcursionToExcursionDTO(excursionService
                            .patchExcursion(excursion, principal)
                            .orElseThrow(() -> new RuntimeException("Excursion not updated")));

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("excursion", excursion);
            System.out.println(e.getMessage());
            return "editExcursion";
        }

        model.addAttribute("excursion", savedExcursion);
        return "redirect:/web/excursions/" + excursion.getId();

    }

    @GetMapping(value = "/{id}/edit")
    public String editExcursionById(@PathVariable("id") String id,
                                    Model model) {
        ExcursionDTO excursion = null;
        try {
            excursion = ExcursionFacade
                    .convertExcursionToExcursionDTO(excursionService
                            .getExcursionById(Long.parseLong(id))
                            .orElseThrow(() -> new RuntimeException("Excursion not updated")));

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("excursion", excursion);
        model.addAttribute("monuments", monumentService.getAllMonuments());
        return "editExcursion";

    }

//    @GetMapping(value = "/{id}/editMonumentsList")
//    public String editeMonumentsList(@PathVariable("id") String id,
//                                     Principal principal,
//                                     Model model) {
//        Excursion excursion = null;
//        try {
//            excursion = excursionService
//                    .getExcursionById(Long.parseLong(id))
//                    .orElseThrow(() -> new RuntimeException("Excursion not updated"));
//
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//        }
//
//        MonumentListEdit monumentListEdit = new MonumentListEdit();
//        monumentListEdit.setId(Long.parseLong(id));
//        monumentListEdit.setMonuments(excursion.getMonuments());
//
//        model.addAttribute("monumentList", monumentListEdit);
//        model.addAttribute("monuments", monumentService.getAllMonuments());
//        return "editMonumentsList";
//
//    }
//
//    @PostMapping(value = "/{id}/editMonumentsList")
//    public String editeMonumentsListPost(@PathVariable("id") String id,
//                                         Principal principal,
//                                         Model model) {
//        ExcursionDTO excursion = null;
//        try {
//            excursion = ExcursionFacade
//                    .convertExcursionToExcursionDTO(excursionService
//                            .getExcursionById(Long.parseLong(id))
//                            .orElseThrow(() -> new RuntimeException("Excursion not updated")));
//
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//        }
//        model.addAttribute("excursion", excursion);
//        return "editMonumentsList";
//
//    }
//
//    @DeleteMapping("/{excursionId}/deleteMonument/{monumentId}")
//    public String DeleteMonumentInExcursion(@PathVariable("excursionId") String excursionId,
//                                            Principal principal,
//                                            @PathVariable("monumentId") String monumentId,
//                                            Model model) {
//
//
//        Excursion excursion = null;
//        try {
//            excursion = excursionService
//                    .getExcursionById(Long.parseLong(excursionId))
//                    .orElseThrow(() -> new RuntimeException("Excursion not updated"));
//
//            MonumentListEdit monumentList = new MonumentListEdit();
//            monumentList.setId(Long.parseLong(excursionId));
//
//            monumentList.setMonuments(excursion
//                    .getMonuments()
//                    .stream()
//                    .filter(x -> !x.getId().equals(Long.parseLong(monumentId)))
//                    .collect(Collectors.toList()));
//
//            excursionService.putMonumentsToExcursion(monumentList, principal);
//
//
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//        }
//        return "redirect:/web/excursions/" + excursionId + "/editMonumentsList";
//
//    }

    @DeleteMapping("/{id}")
    public String DeleteExcursionById(@PathVariable("id") String id, Principal principal) {

        excursionService.deleteExcursion(Long.parseLong(id), principal);

        return "redirect:/web/excursions";
    }

    @GetMapping(value = "/{id}")
    public String getMonumentById(@PathVariable("id") String id,
                                  Principal principal,
                                  Model model) {
        ExcursionDTO excursionDTO = null;
        ExcursionResponse excursionResponse = null;
        try {
            excursionDTO = excursionFacade
                    .convertExcursionToExcursionDTO(excursionService
                            .getExcursionById(Long.parseLong(id))
                            .orElseThrow(() -> new RuntimeException("Monument not found")));

            excursionResponse = excursionResponseFacade.
                    convertExcursionDTOToExcursionResponse(excursionDTO);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }


        model.addAttribute("excursion", excursionResponse);
        model.addAttribute("guide", userService.getUserById(excursionDTO.getGuideId()).get());


        try {

            List<MonumentExcursionResponse> monuments = excursionResponse
                    .getMonuments()
                    .stream()
                    .map(monumentExcursionResponseFacade::convertMonumentDTOToMonumentExcursionResponse)
                    .collect(Collectors.toList());


            model.addAttribute("monuments", monuments);


        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
        }

        return "excursionPage";


    }

    @DeleteMapping("/{excursionId}/deletePhoto")
    public String deleteUserPhoto(Principal principal,
                                  @PathVariable("excursionId") String excursionId,
                                  Model model) {
        try {

            excursionService.deletePhoto(Long.parseLong(excursionId), principal)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }

        return "redirect:/web/excursions/" + excursionId;

    }


}
