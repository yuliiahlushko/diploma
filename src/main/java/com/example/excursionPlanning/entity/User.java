package com.example.excursionPlanning.entity;

import com.example.excursionPlanning.entity.enums.Role;
import jakarta.persistence.*;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
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
public class User implements UserDetails  {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated
    private Role role;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user", fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private  List<Task> tasks = new ArrayList<>();
    @DateTimeFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime createDate;


    @PrePersist
    protected void safeCreateTime() {
        this.createDate = LocalDateTime.now();
    }

    /**
     * Security
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
        return true;
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
