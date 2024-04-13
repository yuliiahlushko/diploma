package com.example.excursionPlanning.controllers.web;

import com.example.excursionPlanning.dto.MonumentDTO;
import com.example.excursionPlanning.payload.web.UserProfileResponse;
import com.example.excursionPlanning.services.interfaces.MonumentService;
import com.example.excursionPlanning.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(value = "/web/monument")
public class MonumentController {

    @Autowired
    MonumentService monumentService;

    @GetMapping("/{id}")
    public String getUser(Principal principal,@PathVariable String id,
                          Model model) {
        try {
            MonumentDTO monument = userFacadeResponse.convertUserToUserFacadeResponse(userService.getCurrentUser(principal)
                    .orElseThrow(() -> new RuntimeException("User not found")));

            model.addAttribute("user", user);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());

        }

        return "userPage";

    }
}
