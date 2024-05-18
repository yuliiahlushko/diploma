package com.example.excursionPlanning.payload.web;

import com.example.excursionPlanning.entity.Monument;
import jakarta.validation.constraints.Future;
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
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcursionRequest {


    @Size(max = 60, message = "The maximum title size is 60 literals")
    private String title;

    @Size(max = 500, message = "The maximum description size is 500 literals")
    private String description;


    private Long price;
    private Long numberOfSeats;

    @Future(message = "Must contain a date that has not yet arrived")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    private LocalDateTime startTime;

    @Future(message = "Must contain a date that has not yet arrived")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    private LocalDateTime endTime;

    private byte[] image;

    private List<Monument> monuments = new ArrayList<>();

}
