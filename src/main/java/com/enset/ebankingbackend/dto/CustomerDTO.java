package com.enset.ebankingbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String nom;
    private String email;
}
