package com.enset.ebankingbackend.repositories;

import com.enset.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNomContains(String keyword);
    @Query("from Customer c WHERE c.nom like :kc")
    List<Customer> searchCustomer(@Param(value="kc") String keyword);
}
