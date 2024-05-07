package com.example.excursionPlanning.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Monument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String city;
    private Integer price;
    private Integer avgGrade = 0;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "monument")
    private List<Grade> grades = new ArrayList<>();


    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "monument")
    private List<Comment> comments = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "monument_excursion_table",
            joinColumns = @JoinColumn(name = "Excursion_ID"),
            inverseJoinColumns = @JoinColumn(name = "Monument_ID"))
    @ToString.Exclude
    private List<Excursion> excursions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> imageModelIds = new ArrayList<>();

    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public Integer getAvgGrade() {
        return Math.toIntExact(Math.round(grades
                .stream()
                .mapToDouble(Grade::getGrade)
                .average()
                .orElse(0)));
    }

    @PrePersist
    protected void safeCreateTime() {
        this.createdDate = LocalDateTime.now();


    }

}
