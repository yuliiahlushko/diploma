package com.example.excursionPlanning.controllers.auth;


import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.services.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/auth/register")
public class WebRegistrationController {

    @Autowired
    private AuthenticationService authenticationService;

    private static final String CREATE_USER_PAGE = "createUser";

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return CREATE_USER_PAGE;
    }


    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("user") UserDTO userDTO,
                                      BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return CREATE_USER_PAGE;
        }

        try {
            authenticationService.register(userDTO);

        } catch (Exception e) {
            model.addAttribute("error","User with this data can`t be created");
            return CREATE_USER_PAGE;
        }
        return "redirect:/auth/login";
    }


}



