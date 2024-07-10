package com.enset.ebankingbackend.security.services;

import com.enset.ebankingbackend.security.entities.AppRole;
import com.enset.ebankingbackend.security.entities.AppUser;
import com.enset.ebankingbackend.security.repository.AppRoleRepository;
import com.enset.ebankingbackend.security.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

// @TODO 6. sixième partie de la stratégie userdetails
@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser = appUserRepository.findByUsernameOrEmail(username, email);

        if(appUser != null){
            throw new RuntimeException("This user is already existe");
        }

        if(!password.equals(confirmPassword)){
            throw new RuntimeException("Passwords not match");
        }

        appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {

        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if(appRole != null){
            throw new RuntimeException("This role is already exist");
        }
        appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null){
            throw new RuntimeException("This user is not existe");
        }

        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if(appRole == null){
            throw new RuntimeException("This role is allready existe");
        }

        appUser.getRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null){
            throw new RuntimeException("This user is not existe");
        }

        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if(appRole == null){
            throw new RuntimeException("This role is allready existe");
        }

        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username){
        return appUserRepository.findByUsername(username);
    }
}
