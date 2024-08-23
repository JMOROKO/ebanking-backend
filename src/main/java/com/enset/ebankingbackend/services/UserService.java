package com.enset.ebankingbackend.services;

import com.enset.ebankingbackend.dto.RoleDTO;
import com.enset.ebankingbackend.dto.UserDTO;
import com.enset.ebankingbackend.exceptions.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDTO createUser(MultipartFile file, UserDTO user) throws PasswordNotMatchException, UserAlreadyExistException, RoleIsBlankException, RoleIsNotExistException;
    UserDTO updateUser(MultipartFile file, UserDTO user);
    UserDTO findById(String userId) throws AppUserNotFoundException;
    List<UserDTO> findAll();
    List<RoleDTO> findAllRole();
    byte[] getPhotoByUserId(@PathVariable String userId) throws IOException;
}
