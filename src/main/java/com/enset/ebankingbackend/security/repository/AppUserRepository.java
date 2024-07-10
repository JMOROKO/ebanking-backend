package com.enset.ebankingbackend.security.repository;

import com.enset.ebankingbackend.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

// @TODO 4. quatrième partie de la strategie userdetails
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
    AppUser findByUsernameOrEmail(String username, String email);
    AppUser findAppUserByEmail(String email);
}
