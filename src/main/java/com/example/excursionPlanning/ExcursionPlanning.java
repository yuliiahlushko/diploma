package com.example.excursionPlanning;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.security.Principal;

@RestController
@SpringBootApplication
public class ExcursionPlanning {

    public static void main(String[] args) {
        SpringApplication.run(ExcursionPlanning.class, args);

    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }



    @RequestMapping("web/tasks")
    public String demo(Principal principal,
                       Model model) {

        return String.format("Hello"+ principal.getName());

    }

}
