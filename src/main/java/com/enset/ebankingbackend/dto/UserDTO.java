package com.enset.ebankingbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String userId;
    private String username;
    private String password;
    private String confirmPassword;
    private String role;
    private String email;
}
