package com.example.NewBankApp.repo;

import com.example.NewBankApp.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
//handle database operations for the entity-Bank
//JPA Repo provides a set of methods for performing CRUD on the entity
public interface BankRepository extends JpaRepository<BankAccount,Long> {
    Optional<BankAccount> findByCustomerName(String customerName);
}
