package com.enset.ebankingbackend.services;

import com.enset.ebankingbackend.dto.RoleDTO;
import com.enset.ebankingbackend.dto.UserDTO;
import com.enset.ebankingbackend.exceptions.*;
import com.enset.ebankingbackend.mappers.BankAccountMapperImpl;
import com.enset.ebankingbackend.security.entities.AppRole;
import com.enset.ebankingbackend.security.entities.AppUser;
import com.enset.ebankingbackend.security.repository.AppRoleRepository;
import com.enset.ebankingbackend.security.repository.AppUserRepository;
import com.enset.ebankingbackend.security.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private AppUserRepository repository;
    private AppRoleRepository roleRepository;
    private BankAccountMapperImpl dtoMapper;
    private UploadService uploadService;
    private final PasswordEncoder passwordEncoder;


    @Override
    //public UserDTO createUser(@RequestParam MultipartFile file, UserDTO user) {
    public UserDTO createUser(MultipartFile file, UserDTO user) throws PasswordNotMatchException, UserAlreadyExistException, RoleIsBlankException, RoleIsNotExistException {

        log.info("Saving new user");

        if(!user.getPassword().equals(user.getConfirmPassword())){
            throw new PasswordNotMatchException("Passwords not match");
        }
        //System.out.println(user.getRole());
        if(user.getRole().isBlank() || user.getRole().isEmpty()){
            throw new RoleIsBlankException("Select role");
        }

        AppUser appUser = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if(appUser != null){
            throw new UserAlreadyExistException("This user is already existe");
        }
        appUser = dtoMapper.fromUserDTO(user);
        if(file != null){
            Map<String, String> fileUploaded = this.uploadService.uploadFile(file, "photo");
            //System.out.println(fileUploaded.get("fileName"));
            appUser.setPhoto(fileUploaded.get("fileName"));
        }


        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setUserId(UUID.randomUUID().toString());

        AppRole appRole = roleRepository.findById(user.getRole()).orElse(null);
        if(appRole == null){
            throw new RoleIsNotExistException("This role is not existe");
        }

        appUser.getRoles().add(appRole);

        AppUser savedUser = repository.save(appUser);

        UserDTO userDTO = dtoMapper.fromAppUser(savedUser);
        return userDTO;
    }

    @Override
    public UserDTO updateUser(MultipartFile file, UserDTO user) {
        log.info("Updating new user");
        AppUser appUser = dtoMapper.fromUserDTO(user);
        AppUser savedUser = repository.save(appUser);
        if(file != null){
            Map<String, String> fileUploaded = this.uploadService.uploadFile(file, "photo");
            appUser.setPhoto(fileUploaded.get("fileName"));
        }
        UserDTO userDTO = dtoMapper.fromAppUser(savedUser);
        return userDTO;
    }

    @Override
    public UserDTO findById(String userId) throws AppUserNotFoundException {
        AppUser appUser = repository.findById(userId).orElse(null);
        if(appUser == null){
            throw new AppUserNotFoundException("Customer not found");
        }

        UserDTO userDTO = dtoMapper.fromAppUser(appUser);
        return userDTO;
    }

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll().stream()
                .map(user -> dtoMapper.fromAppUser(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> findAllRole() {
        return roleRepository.findAll().stream()
                .map(role -> dtoMapper.fromAppRole(role))
                .collect(Collectors.toList());
    }

    @Override
    public byte[] getPhotoByUserId(String userId) throws IOException {
        AppUser appUser = repository.findById(userId).get();
        return Files.readAllBytes(Path.of(URI.create(appUser.getPhoto())));
    }
}
