package com.example.excursionPlanning.payload.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class MonumentExcursionResponse {

    private Long id;
    @NotNull
    private String title;
    private String description;

    @Min(0)
    private Integer price;
    private String city;

    private String image;
}
