package com.enset.ebankingbackend.dto;

import com.enset.ebankingbackend.entities.BankAccount;
import com.enset.ebankingbackend.enums.OperationType;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
