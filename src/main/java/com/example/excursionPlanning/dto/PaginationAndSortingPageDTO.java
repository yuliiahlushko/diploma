package com.example.excursionPlanning.dto;

import jakarta.validation.constraints.Min;

import lombok.Data;

import java.util.List;

@Data
public class PaginationAndSortingPageDTO {

    @Min(0)
    private long totalElements;

    private List<?> content;


}
