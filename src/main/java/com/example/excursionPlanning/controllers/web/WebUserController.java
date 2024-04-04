package com.example.excursionPlanning.controllers.web;


import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.facade.UserFacade;
import com.example.excursionPlanning.payload.web.UpdateUserFormRequest;
import com.example.excursionPlanning.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(value = "/web/user")
public class WebUserController {

    @Autowired
    UserService userService;

    @Autowired
    UserFacade userFacade;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping()
    public String getUser(Principal principal,
                          Model model) {
        try {
            UserDTO currentUser = userFacade.convertUserToUserDTO(userService.getCurrentUser(principal)
                    .orElseThrow(() -> new RuntimeException("User not found")));
            UpdateUserFormRequest user = new UpdateUserFormRequest();
            user.setName(currentUser.getName());
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }
        return "editUser";

    }


    @PatchMapping()
    public String updateUser(@Valid @ModelAttribute("user") UpdateUserFormRequest user,
                             BindingResult bindingResult,
                             Principal principal, Model model) {

        if (bindingResult.hasErrors()) {
            return "editUser";
        }

        try {
            UpdateUserFormRequest response = userService.patchUser(user, principal, passwordEncoder);
            model.addAttribute("user", response);

        } catch (Exception e) {
            UserDTO oldUser = userFacade.convertUserToUserDTO(userService.getCurrentUser(principal)
                    .orElseThrow(() -> new RuntimeException("User not found")));

            UpdateUserFormRequest response = new UpdateUserFormRequest();
            response.setName(oldUser.getName());
            response.setNewPassword(user.getNewPassword());
            model.addAttribute("user", response);
            model.addAttribute("error", e.getMessage());
            return "editUser";
        }
        return "redirect:/web/tasks";


    }

}
