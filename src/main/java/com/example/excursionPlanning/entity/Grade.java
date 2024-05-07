package com.example.excursionPlanning.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private Monument monument;

    private Long userId;
    private String login;
    private Integer grade;

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    @Column(updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    protected void safeCreateTime() {
        this.createDate = LocalDateTime.now();

    }
}
