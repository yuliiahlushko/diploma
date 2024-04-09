package com.example.excursionPlanning.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    @Size(max = 500,message = "The maximum name size is 500 literals")
    private String bio;

    @Email(message = "Wrong email format")
    private String email;

    @Size(max = 40,message = "The maximum name size is 40 literals")
    private String login;

    @Size(min = 8,message = "The min password size is 8 literals")
    private String password;

    private byte[] image;
}
