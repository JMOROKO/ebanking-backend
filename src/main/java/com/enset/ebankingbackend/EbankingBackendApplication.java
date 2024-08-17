package com.enset.ebankingbackend;

import com.enset.ebankingbackend.dto.BankAccountDTO;
import com.enset.ebankingbackend.dto.CurrentAccountDTO;
import com.enset.ebankingbackend.dto.CustomerDTO;
import com.enset.ebankingbackend.dto.SavingAccountDTO;
import com.enset.ebankingbackend.entities.*;
import com.enset.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.enset.ebankingbackend.exceptions.CustomerNotFoundException;
import com.enset.ebankingbackend.mappers.BankAccountMapperImpl;
import com.enset.ebankingbackend.security.services.AccountService;
import com.enset.ebankingbackend.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.List;
import java.util.stream.Stream;


@SpringBootApplication
public class EbankingBackendApplication {

	/*@Autowired
	private PasswordEncoder passwordEncoder;*/
	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}

	// @Bean
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

	//@Bean
	/*CommandLineRunner commandLineRunnerJdbc(JdbcUserDetailsManager jdbcUserDetailsManager){
		return args -> {
			init(jdbcUserDetailsManager);
		};
	}

	private void init(JdbcUserDetailsManager jdbcUserDetailsManager){
		UserDetails user1 = jdbcUserDetailsManager.loadUserByUsername("user1");
		if(user1 != null)
			return ;

		jdbcUserDetailsManager.createUser(
				User.withUsername("user1")
						.password(passwordEncoder.encode("12345"))
						.roles("USER").build()
		);
		jdbcUserDetailsManager.createUser(
				User.withUsername("admin")
						.password(passwordEncoder.encode("12345"))
						.roles("ADMIN").build()
		);
		jdbcUserDetailsManager.createUser(
				User.withUsername("admin2")
						.password(passwordEncoder.encode("12345"))
						.roles("USER","ADMIN").build()
		);
	}*/

	//@Bean
	CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
		return args -> {
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");

			accountService.addNewUser("user1", "12345", "user1@gmail.com", "12345");
			accountService.addNewUser("user2", "12345", "user2@gmail.com", "12345");
			accountService.addNewUser("admin", "12345", "admin@gmail.com", "12345");

			accountService.addRoleToUser("user1", "USER");
			accountService.addRoleToUser("user2", "USER");
			accountService.addRoleToUser("admin", "USER");
			accountService.addRoleToUser("admin", "ADMIN");
		};
	}
}
