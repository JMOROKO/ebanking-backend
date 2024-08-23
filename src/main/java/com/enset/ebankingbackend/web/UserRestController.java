package com.enset.ebankingbackend.web;

import com.enset.ebankingbackend.dto.RoleDTO;
import com.enset.ebankingbackend.dto.UserDTO;
import com.enset.ebankingbackend.exceptions.*;
import com.enset.ebankingbackend.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
//@RequestMapping("/api/v1/allergic-disorder-types")
@Slf4j
@CrossOrigin("*")
public class UserRestController {
    private UserService service;


    @PostMapping(path="/create-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserDTO createUser(@RequestParam("file") MultipartFile file, UserDTO userDTO) throws UserAlreadyExistException, PasswordNotMatchException, RoleIsBlankException, RoleIsNotExistException {
        return this.service.createUser(file, userDTO);
    }
    @PostMapping(path="/update-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserDTO updateUser(@RequestParam("file") MultipartFile file, UserDTO userDTO) {
        return this.service.updateUser(file, userDTO);
    }

    @GetMapping("/users")
    public List<UserDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/roles")
    public List<RoleDTO> findAllRole(){
        return service.findAllRole();
    }

    @GetMapping("/users/{user-id}")
    public UserDTO findById(@PathVariable("user-id")String userId) throws AppUserNotFoundException {
        return service.findById(userId);
    }

    // @GetMapping(path = "/user/{user-id}/file", produces = MediaType.APPLICATION_PDF_VALUE)
    // @GetMapping(path = "/user/{user-id}/file",produces = MediaType.IMAGE_PNG_VALUE)
    @GetMapping(path = "/user/{user-id}/file",produces = MediaType.ALL_VALUE)
    public byte[] getPaymentFile(@PathVariable("user-id") String userId) throws IOException {
        return service.getPhotoByUserId(userId);
    }
}
