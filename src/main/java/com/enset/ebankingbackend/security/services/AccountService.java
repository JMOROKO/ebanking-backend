package com.enset.ebankingbackend.security.services;

import com.enset.ebankingbackend.security.entities.AppRole;
import com.enset.ebankingbackend.security.entities.AppUser;

// @TODO 5. cinquième partie de la stratégie userdetails
public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);

    AppUser loadUserByUsername(String username);
}
