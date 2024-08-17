package com.enset.ebankingbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditDTO {
    private String accountId;
    private double amount;
    private String description;
}
