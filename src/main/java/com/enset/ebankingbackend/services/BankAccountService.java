package com.enset.ebankingbackend.services;

import com.enset.ebankingbackend.dto.*;
import com.enset.ebankingbackend.entities.BankAccount;
import com.enset.ebankingbackend.entities.CurrentAccount;
import com.enset.ebankingbackend.entities.Customer;
import com.enset.ebankingbackend.entities.SavingAccount;
import com.enset.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.enset.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();

    CustomerDTO getCustomer(Long id) throws CustomerNotFoundException;

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfert(String accountIdSource, String accountIdDestination, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO updateCustomer(CustomerDTO request);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountOparationHistrory(String accountId);

    //AccountHistoryDTO getAccountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
