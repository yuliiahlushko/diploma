package com.example.excursionPlanning.entity;

import com.example.excursionPlanning.services.FileLoader;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, columnDefinition = "text")
    private String title;
    private String description;
    @Column(nullable = false)
    private Long guideId;
    private Long price;
    private Long numberOfSeats;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer likes;

    @ElementCollection
    private List<Long> likesUserId = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "monument_excursion_table",
            joinColumns = @JoinColumn(name = "Monument_ID"),
            inverseJoinColumns = @JoinColumn(name = "Excursion_ID"))
    @ToString.Exclude
    private List<Monument> monuments = new ArrayList<>();


    @Lob
    private byte[] image;

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    @Column(updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    protected void safeCreateTime() {
        this.createDate = LocalDateTime.now();
        try {
            this.image = new FileLoader().loadFileAsBytes("image-icon.jpg");

            // Сохраняем массив байтов в поле объекта или переменную, где вам нужно будет использовать его
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
