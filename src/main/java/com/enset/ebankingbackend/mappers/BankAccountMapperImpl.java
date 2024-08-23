package com.enset.ebankingbackend.mappers;

import com.enset.ebankingbackend.dto.*;
import com.enset.ebankingbackend.entities.AccountOperation;
import com.enset.ebankingbackend.entities.CurrentAccount;
import com.enset.ebankingbackend.entities.Customer;
import com.enset.ebankingbackend.entities.SavingAccount;
import com.enset.ebankingbackend.security.entities.AppRole;
import com.enset.ebankingbackend.security.entities.AppUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        savingAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDTO;
    }
    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        currentAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDTO;
    }
    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }
    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
        return currentAccount;
    }

    public AccountOperationDTO fromAccountOparation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }

    public AccountOperation fromAccountOparation(AccountOperationDTO accountOperationDTO){
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO, accountOperation);
        return accountOperation;
    }

    public UserDTO fromAppUser(AppUser appUser){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(appUser, userDTO);
        return userDTO;
    }
    public AppUser fromUserDTO(UserDTO userDTO){
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(userDTO, appUser);
        List<AppRole> emptyRoles = new ArrayList<>();
        appUser.setRoles(emptyRoles);
        return appUser;
    }

    public RoleDTO fromAppRole(AppRole role){
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(role, roleDTO);
        return roleDTO;
    }
    public AppRole fromRoleDTO(RoleDTO roleDTO){
        AppRole appRole = new AppRole();
        BeanUtils.copyProperties(roleDTO, appRole);
        return appRole;
    }
}
