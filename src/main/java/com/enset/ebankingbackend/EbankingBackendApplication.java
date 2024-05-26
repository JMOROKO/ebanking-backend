package com.enset.ebankingbackend;

import com.enset.ebankingbackend.dto.BankAccountDTO;
import com.enset.ebankingbackend.dto.CurrentAccountDTO;
import com.enset.ebankingbackend.dto.CustomerDTO;
import com.enset.ebankingbackend.dto.SavingAccountDTO;
import com.enset.ebankingbackend.entities.*;
import com.enset.ebankingbackend.enums.AccountStatus;
import com.enset.ebankingbackend.enums.OperationType;
import com.enset.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.enset.ebankingbackend.exceptions.CustomerNotFoundException;
import com.enset.ebankingbackend.mappers.BankAccountMapperImpl;
import com.enset.ebankingbackend.repositories.AccountOperationRepository;
import com.enset.ebankingbackend.repositories.BankAccountRepository;
import com.enset.ebankingbackend.repositories.CustomerRepository;
import com.enset.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BankAccountService bankAccountService, BankAccountMapperImpl dtoMapper){
		return args -> {
			Stream.of("Hassan", "Imane", "Mohamed").forEach(name -> {
				Customer customer = new Customer();
				customer.setNom(name);
				customer.setEmail(name+"@gmail.com");
				CustomerDTO request = dtoMapper.fromCustomer(customer);
				bankAccountService.saveCustomer(request);

				bankAccountService.listCustomers().forEach(cust -> {
					try{
						bankAccountService.saveCurrentBankAccount(Math.random()*9000, 900, cust.getId());
						bankAccountService.saveSavingBankAccount(Math.random()*12000, 5.5, cust.getId());

						List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();

						for(BankAccountDTO bankAccount: bankAccounts){
							for(int i = 0; i<=10; i++){
								String accountId;
								if(bankAccount instanceof SavingAccountDTO){
									accountId = ((SavingAccountDTO) bankAccount).getId();
								}else{
									accountId = ((CurrentAccountDTO) bankAccount).getId();
								}
								bankAccountService.credit(accountId, 10000+Math.random()*12000, "Credit");
								bankAccountService.debit(accountId, 10000+Math.random()*9000, "Debit");
							}
						}
					}
					catch (CustomerNotFoundException e){
						e.printStackTrace();
					} catch (BankAccountNotFoundException | BalanceNotSufficentException e) {
						e.printStackTrace();
					}
				});
			});
		};
	}
}
