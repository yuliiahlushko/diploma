package com.example.excursionPlanning.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ImageModelDTO {

    @NotNull
    private Long id;

    @NotNull
    private byte[] imageBytes;

    @NotNull
    private Long monumentId;
}
