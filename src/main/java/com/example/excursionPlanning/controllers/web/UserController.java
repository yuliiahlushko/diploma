package com.example.excursionPlanning.controllers.web;


import com.example.excursionPlanning.dto.UserDTO;
import com.example.excursionPlanning.facade.UserFacadeRequest;
import com.example.excursionPlanning.facade.UserFacadeResponse;
import com.example.excursionPlanning.payload.web.UpdateUserFormRequest;
import com.example.excursionPlanning.payload.web.UserProfileResponse;
import com.example.excursionPlanning.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Base64;

@Controller
@RequestMapping(value = "/web/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserFacadeRequest userFacadeRequest;

    @Autowired
    UserFacadeResponse userFacadeResponse;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping()
    public String getUserForUpdate(Principal principal,
                                   Model model) {
        try {
            UserDTO currentUser = userFacadeRequest.convertUserToUserDTO(userService.getCurrentUser(principal)
                    .orElseThrow(() -> new RuntimeException("User not found")));

            UpdateUserFormRequest user = new UpdateUserFormRequest();

            user.setLogin(currentUser.getLogin());
            user.setEmail(currentUser.getEmail());
            user.setBio(currentUser.getBio());


            model.addAttribute("user", user);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }

        return "editUser";

    }

    @GetMapping("/profile")
    public String getUser(Principal principal,
                          Model model) {
        try {
            UserProfileResponse user = userFacadeResponse.convertUserToUserFacadeResponse(userService.getCurrentUser(principal)
                    .orElseThrow(() -> new RuntimeException("User not found")));

            model.addAttribute("user", user);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }

        return "userPage";

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
            UserDTO oldUser = userFacadeRequest.convertUserToUserDTO(userService.getCurrentUser(principal)
                    .orElseThrow(() -> new RuntimeException("User not found")));

            UpdateUserFormRequest response = new UpdateUserFormRequest();

            response.setLogin(oldUser.getLogin());
            response.setEmail(oldUser.getEmail());

            response.setBio(oldUser.getBio());


            model.addAttribute("user", response);
            model.addAttribute("error", e.getMessage());
            return "editUser";
        }
        return "redirect:/web/user/profile";


    }

    @DeleteMapping("/deletePhoto")
    public String deleteUserPhoto(Principal principal,
                                  Model model) {
        try {
            userService.deletePhoto(principal)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }

        return "redirect:/web/user/profile";

    }
}
