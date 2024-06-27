package com.example.NewBankApp.service;

import com.example.NewBankApp.entity.BankAccount;
import com.example.NewBankApp.repo.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//Service class for managing BankAccount entities.
//handle business logic for our REST API
public class BankService {
    private BankRepository bankRepository;

    @Autowired

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<BankAccount> getAllAccounts() {
        return bankRepository.findAll();
    }

    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return bankRepository.save(bankAccount);
    }

    public Optional<BankAccount> getProductById(Long id) {
        return bankRepository.findById(id);
    }

    public int getBalance(Long id) {
        return bankRepository.findById(id).get().getBalance();
    }

    public ResponseEntity<String> deleteById(Long id) {
        bankRepository.deleteById(id);
        return ResponseEntity.ok(
                "Product Deleted Successfully");
    }

    public void deposit(int amount, long id) {
        BankAccount account = bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        if (amount <= 0) {
            System.out.println("Deposit must be higher than 0");
            throw new IllegalArgumentException("Deposit must be higher than 0");
        } else {
            account.increase(amount);
            bankRepository.save(account);
        }
    }

    public void withdraw(int amount, long id) {
        BankAccount account = bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount > getBalance(id)) {
            System.out.println("Insufficient funds");
            throw new IllegalArgumentException("Insufficient funds");
        }
        if (amount <= 0) {
            System.out.println("Unsupported operation");
            throw new UnsupportedOperationException("Unsupported operation");
        } else {
            account.decrease(amount);
            bankRepository.save(account);
        }

    }

}

