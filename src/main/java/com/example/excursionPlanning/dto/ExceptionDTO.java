package com.example.excursionPlanning.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {

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
    private Integer likes;

    private byte[] image;

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime createDate;
}
