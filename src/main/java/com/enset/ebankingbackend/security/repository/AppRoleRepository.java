package com.enset.ebankingbackend.security.repository;

import com.enset.ebankingbackend.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

// @TODO 3. troisième partie de la strategie userdetails
public interface AppRoleRepository extends JpaRepository<AppRole, String> {
}
