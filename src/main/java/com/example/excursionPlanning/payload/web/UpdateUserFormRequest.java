package com.example.excursionPlanning.payload.web;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserFormRequest {

    @Size(max = 40,message = "The maximum name size is 40 literals")
    private String name;
    

    @Pattern(regexp = "^[^\\s]{8,}$", message = "The min password size is 8 literals")
    private String newPassword;
}
