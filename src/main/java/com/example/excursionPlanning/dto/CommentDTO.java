package com.example.excursionPlanning.dto;

import com.example.excursionPlanning.entity.Monument;
import jakarta.persistence.*;
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
public class CommentDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    @Column(nullable = false)
    private String userLogin;

    @Size(max = 500, message = "The maximum comment size is 500 literals")
    private String message;

    @NotNull
    private Monument monument;

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime createDate;
}
