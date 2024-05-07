package com.example.excursionPlanning.payload.web;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
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
public class CommentResponse {

    @NotNull
    private Long id;


    @Size(max = 500, message = "The maximum comment size is 500 literals")
    private String message;
}
