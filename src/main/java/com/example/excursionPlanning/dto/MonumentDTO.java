package com.example.excursionPlanning.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
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
public class MonumentDTO {

    @NotNull
    private Long id;
    @NotNull
    private String title;
    private String description;
    private Integer price;
    private Integer avgGrade = 0;

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime createDate;
}

