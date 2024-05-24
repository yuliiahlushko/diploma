package com.example.excursionPlanning.payload.web;

import com.example.excursionPlanning.entity.Monument;
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
import java.util.List;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcursionResponse {

    @NotNull
    private Long id;

    @Size(max = 60, message = "The maximum title size is 60 literals")
    private String title;

    @Size(max = 500, message = "The maximum description size is 500 literals")
    private String description;

    @NotNull
    private Long guideId;

    private Long price;
    private Long numberOfSeats;


    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime endTime;

    @Min(value = 0, message = "count of likes can/'t be less zero")
    private Integer likes = 0;

    private String image;

    private List<Monument> monuments = null;


    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime createDate;


}
