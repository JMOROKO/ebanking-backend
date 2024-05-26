package com.enset.ebankingbackend.web;

import com.enset.ebankingbackend.dto.AccountHistoryDTO;
import com.enset.ebankingbackend.dto.AccountOperationDTO;
import com.enset.ebankingbackend.dto.BankAccountDTO;
import com.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.enset.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccount(){
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/account/{accountId}/opatations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountOparationHistrory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getHistory(@PathVariable String accountId
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }
}