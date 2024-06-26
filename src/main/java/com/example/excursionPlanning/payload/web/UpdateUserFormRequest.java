package com.example.excursionPlanning.payload.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserFormRequest {


    private String bio;


    private String email;


    private String login;

    private MultipartFile newImage;


    private String newPassword;
}
