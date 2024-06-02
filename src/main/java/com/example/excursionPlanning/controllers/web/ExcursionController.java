package com.example.excursionPlanning.controllers.web;

import com.example.excursionPlanning.dto.ExcursionDTO;
import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.Excursion;
import com.example.excursionPlanning.entity.Monument;
import com.example.excursionPlanning.entity.enums.Role;
import com.example.excursionPlanning.facade.*;
import com.example.excursionPlanning.paginationandsorting.PageSettings;
import com.example.excursionPlanning.payload.web.*;
import com.example.excursionPlanning.services.interfaces.ExcursionService;
import com.example.excursionPlanning.services.interfaces.ImageModelService;
import com.example.excursionPlanning.services.interfaces.MonumentService;
import com.example.excursionPlanning.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private ExcursionsResponseFacade excursionsResponseFacade;

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
            if (excursionRequest.getEndTime().isBefore(excursionRequest.getStartTime())) {
                throw (new RuntimeException("Start time cant be after end time"));
            }
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

    @GetMapping(value = "/{id}/getTicket")
    public String getTicketOnExcursion(Principal principal,
                                       @PathVariable String id,
                                       Model model) {

        try {
            excursionService.getTicket(Long.parseLong(id), principal);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }
        return "redirect:/web/excursions/" + id;

    }

    @GetMapping()
    public String getAllExcursion(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "size", required = false) Integer size,
                                  @RequestParam(value = "price", required = false) String price,
                                  @RequestParam(value = "startTime", required = false)
                                  @DateTimeFormat(pattern = "HH:mm:ss dd-MM-yyyy") LocalDateTime startTime,
                                  @RequestParam(value = "endTime", required = false)
                                  @DateTimeFormat(pattern = "HH:mm:ss dd-MM-yyyy") LocalDateTime endTime,
                                  Model model) {

        try {

            PageSettings pageSettings = new PageSettings(page, size);
            Pageable pageable = PageRequest.of(pageSettings.getPage(), pageSettings.getSize());


            List<Excursion> allExcursions = null;


            if (price != null && !price.isEmpty()) {

                if (startTime != null || endTime != null) {

                    if (startTime == null) startTime = LocalDateTime.now();
                    if (endTime == null) endTime = LocalDateTime.now().plusYears(1);

                    allExcursions = excursionService
                            .getExcursionsByTime(startTime, endTime, pageable)
                            .stream()
                            .filter((x) -> x.getPrice() <= Long.parseLong(price))
                            .collect(Collectors.toList());


                } else {

                    allExcursions = excursionService.getExcursionsByPrice(0L, Long.parseLong(price), pageable)
                            .stream().toList();
                }


            } else {


                if (startTime == null) startTime = LocalDateTime.now().minusYears(1);
                if (endTime == null) endTime = LocalDateTime.now().plusYears(1);


                allExcursions = excursionService
                        .getExcursionsByTime(startTime, endTime, pageable)
                        .stream().toList();


            }


            List<ExcursionsResponse> excursions = allExcursions
                    .stream()
                    .map(excursionsResponseFacade::convertExcursionToExcursionsResponse)
                    .toList();

            model.addAttribute("excursions", excursions);


        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());


        }
        return "excursions";
    }

    @PatchMapping()
    public String updateExcursionInfo(@Valid @ModelAttribute("excursion") ExcursionEditRequest excursion,
                                      BindingResult bindingResult,
                                      Principal principal, Model model) {


        if (bindingResult.hasErrors()) {
            return "editExcursion";
        }
        if (excursion.getEndTime().isAfter(excursion.getStartTime())) {
            throw (new RuntimeException("Start time cant be after end time"));
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
            boolean hasAbility = false;
            if (principal != null) {
                hasAbility = userService.getCurrentUser(principal)
                        .map(user -> user.getRole().equals(Role.ADMIN))
                        .orElse(false);

                if (!hasAbility) {
                    hasAbility = excursionService.getExcursionById(Long.parseLong(id))
                            .get()
                            .getGuideId()
                            .equals(userService.getCurrentUser(principal).get().getId());

                }
                model.addAttribute("hasAbility", hasAbility);
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
        }

        Boolean isHaveTicket = excursionService.getExcursionById(Long.parseLong(id))
                .get()
                .getSeatsUserId()
                .contains(userService.getCurrentUser(principal).get().getId());

        model.addAttribute("isHaveTicket", isHaveTicket);
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

    @ResponseBody
    @GetMapping("/search")
    public List<ExcursionsResponse> searchMonuments(@RequestParam(value = "query") String query) {


        return
                excursionService
                        .getExcursionsByTitle(query)
                        .stream()
                        .map(excursionsResponseFacade::convertExcursionToExcursionsResponse)
                        .toList();


    }

}
