package com.example.excursionPlanning.controllers.auth;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/auth")
public class LoginController {

//    @PostMapping(value = "/login",consumes = "application/json")
//    public String getRestLogin(){
//        return "redirect:/auth/api/login";
//    }

    @RequestMapping(value = "/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

}
