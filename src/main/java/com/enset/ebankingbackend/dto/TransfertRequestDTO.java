package com.enset.ebankingbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransfertRequestDTO {
    private String accountSource;
    private String accountDestination;
    private double amount;
    private String description;
}
