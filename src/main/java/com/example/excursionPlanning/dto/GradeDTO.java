package com.example.excursionPlanning.dto;

import com.example.excursionPlanning.entity.Monument;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class GradeDTO {

    @NotNull
    private Long id;

    @NotNull
    private Monument monument;

    private Long userId;
    private String login;

    @NotNull
    @Max(10)
    @Min(1)
    private Integer grade;

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime createDate;

    @Override
    public String toString() {
        return "GradeDTO{" +
                "id=" + id +
                ", monument=" + monument +
                ", userId=" + userId +
                ", login='" + login + '\'' +
                ", grade=" + grade +
                ", createDate=" + createDate +
                '}';
    }
}
