package com.example.excursionPlanning.entity;

import com.example.excursionPlanning.entity.enums.Role;
import com.example.excursionPlanning.services.FileLoader;
import jakarta.persistence.*;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@EqualsAndHashCode
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] image;

    @Column(columnDefinition = "text")
    private String bio;

    @Column(unique = true)
    @Size(max = 40, message = "The maximum name size is 40 literals")
    private String login;

    @Column(unique = true)
    @Email
    private String email;

    private boolean isLocked = false;

    @Pattern(regexp = "^[^\\s]{8,}$", message = "The min password size is 8 literals")
    private String password;

    @Enumerated
    private Role role;


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

    /**
     * Security
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public boolean lock() {

        return this.isLocked = true;
    }

    public boolean nonLock() {

        return this.isLocked = false;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
