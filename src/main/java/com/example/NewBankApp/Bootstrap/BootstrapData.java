package com.example.NewBankApp.Bootstrap;

import com.example.NewBankApp.entity.BankAccount;
import com.example.NewBankApp.repo.BankRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class BootstrapData implements CommandLineRunner {
    public BankRepository bankRepository;

    public BootstrapData(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        BankAccount eric= new BankAccount(123,"Eric C",100);
//        bankRepository.save(eric);
//        BankAccount michael= new BankAccount(123,"Michael",100);
//        bankRepository.save(michael);
    }
}
