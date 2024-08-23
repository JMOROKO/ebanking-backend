package com.enset.ebankingbackend.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// @TODO 1. première partie de la strategie userdetails
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String photo;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
