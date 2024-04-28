package com.example.excursionPlanning.controllers.web;

import com.example.excursionPlanning.dto.ImageModelDTO;
import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.entity.ImageModel;
import com.example.excursionPlanning.entity.Monument;
import com.example.excursionPlanning.facade.ImageModelFacadeResponse;
import com.example.excursionPlanning.facade.MonumentFacade;
import com.example.excursionPlanning.facade.MonumentRequestFacade;
import com.example.excursionPlanning.facade.MonumentResponseFacade;
import com.example.excursionPlanning.paginationandsorting.PageSettings;
import com.example.excursionPlanning.payload.web.*;
import com.example.excursionPlanning.services.interfaces.ImageModelService;
import com.example.excursionPlanning.services.interfaces.MonumentService;
import com.example.excursionPlanning.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/web/monuments")
public class MonumentController {

    @Autowired
    private MonumentService monumentService;

    @Autowired
    private ImageModelService imageModelService;

    @Autowired
    private ImageModelFacadeResponse imageModelFacadeResponse;

    @Autowired
    private MonumentFacade monumentFacade;

    @Autowired
    private MonumentRequestFacade monumentRequestFacade;

    @Autowired
    private MonumentResponseFacade monumentResponseFacade;


    @GetMapping(value = "/new")
    public String getFormForCreateNewMonument(Model model) {


        model.addAttribute("monument", new MonumentDTO());
        return "createMonument";


    }

    @PostMapping(value = "/new")
    public String createMonument(@Valid @ModelAttribute("monument") MonumentRequest monumentRequest,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) {

        if (bindingResult.hasErrors()) {
            return "createMonument";
        }

        try {
            monumentService.createMonument(monumentRequestFacade
                            .convertMonumentRequestToMonumentDTO(monumentRequest), principal)
                    .orElseThrow(() -> new RuntimeException("Monument can't be created"));

        } catch (RuntimeException e) {

            model.addAttribute("error", e.getMessage());
            return "createMonument";
        }
        return "redirect:/web/monuments";


    }

    @GetMapping()
    @ResponseBody
    public String getAllMonuments(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "size", required = false) Integer size,
                                  @RequestParam(value = "city", required = false) String city,
                                  Model model) {


        try {

            PageSettings pageSettings = new PageSettings(page, size);

            List<MonumentDTO> monuments = monumentService
                    .getAllMonuments(pageSettings)
                    .stream()
                    .map(monumentFacade::convertMonumentToMonumentDTO)
                    .toList();


            model.addAttribute("monuments", monuments);

            return monuments.get(0).getTitle();

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "monuments";
    }


    @PatchMapping()
    public String updateMonumentInfo(@Valid @ModelAttribute("monument") MonumentResponse monumentResponse,
                                     BindingResult bindingResult,
                                     Principal principal, Model model) {

        if (bindingResult.hasErrors()) {
            return "editMonument";
        }

        MonumentDTO monumentDTO = null;
        try {
            monumentDTO = monumentFacade.convertMonumentToMonumentDTO(monumentService
                    .patchMonument(monumentResponseFacade
                            .convertMonumentResponseToMonumentDTO(monumentResponse), principal)
                    .orElseThrow(() -> new RuntimeException("Monument not updated")));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("monument", monumentDTO);
        return "monumentPage";

    }

    @GetMapping(value = "/{id}")
    public String getMonumentById(@PathVariable("id") String id,
                                  Principal principal,
                                  Model model) {
        MonumentDTO monument = null;
        try {
            monument = monumentFacade
                    .convertMonumentToMonumentDTO(monumentService.getMonumentById(Long.parseLong(id), principal)
                            .orElseThrow(() -> new RuntimeException("Monument not found")));

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("monument", monument);


        try {

            List<ImageModelResponse> images = imageModelService
                    .getAllImageModelsByMonumentId(Long.parseLong(id))
                    .stream()
                    .map(ImageModelFacadeResponse::convertImageModelToImageModelResponse)
                    .toList();

            if (images.size() > 1) {
                images = images.stream().skip(1).toList();
            }


            model.addAttribute("images", images);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }

        return "monumentPage";


    }

    @GetMapping(value = "/{id}/edit")
    public String editMonumentById(@PathVariable("id") String id,
                                   Principal principal,
                                   Model model) {
        MonumentDTO monument = null;
        try {
            monument = monumentFacade
                    .convertMonumentToMonumentDTO(monumentService
                            .getMonumentById(Long.parseLong(id), principal)
                            .orElseThrow(() -> new RuntimeException("Monument not found")));

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("monument", monument);
        return "editMonument";

    }

    @GetMapping(value = "/{id}/editPhotos")
    public String editMonumentPhotosById(@PathVariable("id") String id,
                                         Model model) {

        try {

            List<ImageModelResponse> images = imageModelService
                    .getAllImageModelsByMonumentId(Long.parseLong(id))
                    .stream()
                    .map(ImageModelFacadeResponse::convertImageModelToImageModelResponse)
                    .toList();

            if (images.size() > 1) {
                images = images.stream().skip(1).toList();
            }

            model.addAttribute("images", images);
            model.addAttribute("id", id);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }
        return "editMonumentsPhotos";

    }

    @PostMapping(value = "/{id}/uploadPhoto")
    public String uploadPhoto(@PathVariable("id") Long id,
                              Principal principal,
                              MultipartFile file,
                              Model model) {


        if (file.isEmpty()) {

            model.addAttribute("error", "emptyFile");
        } else {
            ImageModelDTO imageModelDTO = new ImageModelDTO();
            try {
                imageModelDTO.setImageBytes(file.getBytes());
                imageModelDTO.setMonumentId(id);
                imageModelService.createImageModel(imageModelDTO, principal);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/web/monuments/" + id.toString() + "/editPhotos";
    }

    @DeleteMapping(value = "/{id}/deletePhoto/{photoId}")
    public String deletePhoto(@PathVariable("id") Long id,
                              @PathVariable("photoId") Long photoId,
                              Principal principal) {

        imageModelService.deleteImageModel(photoId, principal);

        String url = "/web/monuments/" + id.toString() + "/editPhotos";
        return "redirect:" + url;
    }


    @DeleteMapping("/{id}")
    public String DeleteMonumentById(@PathVariable("id") String id, Principal principal) {

        monumentService.deleteMonument(Long.parseLong(id), principal);
        return "redirect:/web/user/profile";
    }

}
