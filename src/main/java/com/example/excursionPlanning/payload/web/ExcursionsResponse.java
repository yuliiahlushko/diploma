package com.example.excursionPlanning.payload.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcursionsResponse {

    @NotNull
    private Long id;

    @Size(max = 60, message = "The maximum title size is 60 literals")
    private String title;


    private Long price;


    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime endTime;

    @Min(value = 0, message = "count of likes can/'t be less zero")
    private Integer likes = 0;

    private String image;
}
