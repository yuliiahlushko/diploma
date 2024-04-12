package com.example.excursionPlanning.entity;

import com.example.excursionPlanning.services.FileLoader;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String userLogin;

    @Column(nullable = false, columnDefinition = "text")
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    private Monument monument;

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    @Column(updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    protected void safeCreateTime() {
        this.createDate = LocalDateTime.now();

    }
}
