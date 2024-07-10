package com.enset.ebankingbackend.security.services;

import com.enset.ebankingbackend.security.entities.AppUser;
import com.enset.ebankingbackend.security.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// @TODO 7. partie de la stratégie userdetails
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //recherche de l'utilisateur

        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) {
            System.out.println(String.format("User %s not found", username));
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        //obtenir une liste de user
        String[] roles = appUser.getRoles().stream().map(u -> u.getRole()).toArray(String[]::new);
        //creation de l'objet userdetails
        UserDetails userDetails = User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles)
                .build();
        return userDetails;
    }
}
