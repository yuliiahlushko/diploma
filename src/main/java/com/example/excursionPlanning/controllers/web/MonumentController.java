package com.example.excursionPlanning.controllers.web;

import com.example.excursionPlanning.dto.CommentDTO;
import com.example.excursionPlanning.dto.GradeDTO;
import com.example.excursionPlanning.dto.ImageModelDTO;
import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.entity.Comment;
import com.example.excursionPlanning.entity.Grade;
import com.example.excursionPlanning.entity.Monument;
import com.example.excursionPlanning.entity.enums.Role;
import com.example.excursionPlanning.facade.*;
import com.example.excursionPlanning.paginationandsorting.PageSettings;
import com.example.excursionPlanning.payload.web.*;
import com.example.excursionPlanning.services.interfaces.*;
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
import java.util.Optional;

@Controller
@RequestMapping(value = "/web/monuments")
public class MonumentController {

    @Autowired
    private MonumentService monumentService;

    @Autowired
    private ImageModelService imageModelService;

    @Autowired
    private ExcursionService excursionService;

    @Autowired
    private ExcursionsResponseFacade excursionsResponseFacade;

    @Autowired
    private MonumentFacade monumentFacade;

    @Autowired
    private MonumentRequestFacade monumentRequestFacade;

    @Autowired
    private MonumentResponseFacade monumentResponseFacade;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private MonumentExcursionResponseFacade monumentExcursionResponseFacade;

    @GetMapping(value = "/new")
    public String getFormForCreateNewMonument(Model model, Principal principal) {


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

    @ResponseBody
    @GetMapping("/search")
    public List<MonumentDTO> searchMonuments(@RequestParam(value = "query") String query) {


        return monumentService
                .getMonumentByTitle(query)
                .stream()
                .map(monumentFacade::convertMonumentToMonumentDTO)
                .toList();

    }

    @GetMapping()
    public String getAllMonuments(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "size", required = false) Integer size,
                                  @RequestParam(value = "city", required = false) String city,
                                  @RequestParam(value = "minPrice", required = false) String minPrice,
                                  @RequestParam(value = "maxPrice", required = false) String maxPrice,
                                  Model model) {

        try {

            PageSettings pageSettings = new PageSettings(page, size);

            List<Monument> allMonuments = null;

            if (city != null) {

                if (minPrice != null || maxPrice != null) {

                    if (minPrice == null) minPrice = "0";
                    if (maxPrice == null) maxPrice = "1000000";

                    allMonuments = monumentService
                            .getMonumentsByPrice(Long.parseLong(minPrice), Long.parseLong(maxPrice))
                            .stream()
                            .filter((x) -> x.getCity().equals(city))
                            .toList();

                } else {

                    allMonuments = monumentService.getMonumentsByCity(city, pageSettings);
                }


            } else {
                if (minPrice != null || maxPrice != null) {

                    if (minPrice == null) minPrice = "0";
                    if (maxPrice == null) maxPrice = "1000000";

                    allMonuments = monumentService
                            .getMonumentsByPrice(Long.parseLong(minPrice), Long.parseLong(maxPrice));

                } else {

                    allMonuments = monumentService.getAllMonuments(pageSettings);

                }

            }


            List<MonumentExcursionResponse> monuments = allMonuments
                    .stream()
                    .map(monumentExcursionResponseFacade::convertMonumentDTOToMonumentExcursionResponse)
                    .toList();


            model.addAttribute("monuments", monuments);
            model.addAttribute("cities", monumentService.getAllCities());


        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

            System.out.println(e.getMessage());
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

            List<CommentDTO> comments = commentService
                    .getAllCommentsByMonumentId(Long.parseLong(id))
                    .stream()
                    .map(CommentFacade::convertCommentToCommentDTO)
                    .toList();


            Integer grade = 0;
            if (gradeService.getGradeByUserIdAndMonumentId(Long.parseLong(id), principal).isPresent()) {
                grade = gradeService.getGradeByUserIdAndMonumentId(Long.parseLong(id), principal)
                        .get().getGrade();
            }
            model.addAttribute("images", images);
            model.addAttribute("comments", comments);
            model.addAttribute("grade", grade);
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

            model.addAttribute("isUser", hasAbility);
            List<ExcursionsResponse> excursions = excursionService
                    .getExcursionByMonumentId(Long.parseLong(id))
                    .stream()
                    .map(excursionsResponseFacade::convertExcursionToExcursionsResponse)
                    .toList();

            model.addAttribute("excursions", excursions);

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
                                         Model model, Principal principal) {


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
        return "redirect:/web/monuments";
    }

    @PostMapping("/{monumentId}/addGrade")
    public String addGrade(@PathVariable("monumentId") String monumentId,
                           Principal principal, @RequestParam("rating") int rating) {

        Optional<Grade> grade = gradeService.getGradeByUserIdAndMonumentId(Long.parseLong(monumentId), principal);

        GradeDTO gradeDTO = new GradeDTO();
        gradeDTO.setMonument(monumentService.getMonumentById(Long.parseLong(monumentId), principal)
                .orElseThrow(() -> new RuntimeException("Monument not exist")));
        gradeDTO.setGrade(rating);
        gradeDTO.setLogin(principal.getName());

        if (grade.isPresent()) {

            gradeDTO.setId(grade.get().getId());
            gradeService.patchGrade(gradeDTO, principal);

        } else {

            gradeService.createGrade(gradeDTO, principal);
        }


        return "redirect:/web/monuments/" + monumentId;
    }

    @PostMapping("/{monumentId}/addComment")
    public String addComment(@PathVariable("monumentId") String monumentId,
                             Principal principal, @RequestParam("message") String message) {

        CommentDTO newComment = new CommentDTO();
        newComment.setMonument(monumentService.getMonumentById(Long.parseLong(monumentId), principal)
                .orElseThrow(() -> new RuntimeException("Monument not exist")));
        newComment.setMessage(message);
        newComment.setUserLogin(principal.getName());


        commentService.createComment(newComment, principal);

        return "redirect:/web/monuments/" + monumentId;
    }

    @PostMapping("/{monumentId}/editComment/{id}")
    public String editComment(@PathVariable("monumentId") String monumentId,
                              @PathVariable("id") String id,
                              @RequestParam("message") String message,
                              Principal principal) {

        Optional<Comment> comm = commentService.getCommentById(Long.parseLong(id), principal);

        if (comm.isPresent() && (comm.get().getUserLogin().equals(principal.getName()) ||
                userService.getCurrentUser(principal).get().getRole().equals(Role.ADMIN))) {

            CommentDTO newComm = new CommentDTO();
            newComm.setId(comm.get().getId());
            newComm.setMonument(comm.get().getMonument());
            newComm.setCreateDate(comm.get().getCreateDate());
            newComm.setUserId(comm.get().getUserId());
            newComm.setMessage(message);
            newComm.setUserLogin(comm.get().getUserLogin());


            commentService.patchComment(newComm, principal);
        }

        return "redirect:/web/monuments/" + monumentId;
    }

    @DeleteMapping("/{monumentId}/deleteComment/{id}")
    public String deleteComment(@PathVariable("monumentId") String monumentId,
                                @PathVariable("id") String id,
                                Principal principal) {

        Optional<Comment> comm = commentService.getCommentById(Long.parseLong(id), principal);

        if (comm.isPresent() && (comm.get().getUserLogin().equals(principal.getName()) ||
                userService.getCurrentUser(principal).get().getRole().equals(Role.ADMIN))) {


            commentService.deleteComment(Long.parseLong(id), principal);
        }

        return "redirect:/web/monuments/" + monumentId;
    }

    @ModelAttribute("isadmin")
    public boolean isAdmin(Principal principal) {

        boolean hasAbility = false;
        if (principal != null) {
            hasAbility = userService.getCurrentUser(principal)
                    .map(user -> user.getRole().equals(Role.ADMIN))
                    .orElse(false);


        }
        return hasAbility;
    }

}
