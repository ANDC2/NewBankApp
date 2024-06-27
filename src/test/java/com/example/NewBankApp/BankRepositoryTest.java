package com.example.NewBankApp;

import com.example.NewBankApp.entity.BankAccount;
import com.example.NewBankApp.repo.BankRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

@DataJpaTest //using to configure the application for testing JPA repo
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // autoconfigure the test database
public class BankRepositoryTest {
    @Autowired
    private BankRepository bankRepository;

    @Test
    public void BankRepository_SaveAll_ReturnsSavedAccount() {
        //@Builder annotation produces complex builder APIs for classes
        // produce the code required to have the class instantiable with code such Bank.builder().customerName("Mary.F")
        BankAccount account = BankAccount.builder().customerName("Mary H").balance(500).build();
        bankRepository.save(account);

        assertEquals(account.getCustomerName(),"Mary H");
        assertEquals(account.getBalance(), 500);
        Assertions.assertThat(account.getId()).isGreaterThan(0);


    }

    @Test
    public void BankRepository_FindAll_ReturnMoreThenOneAccount() {
        BankAccount account = BankAccount.builder().customerName("Mary H").balance(500).build();
        BankAccount account2 = BankAccount.builder().customerName("Mary H").balance(500).build();
        bankRepository.save(account);
        bankRepository.save(account2);
        List<BankAccount> accountList = bankRepository.findAll();

        Assertions.assertThat(accountList).isNotNull();
        Assertions.assertThat(accountList.size()).isEqualTo(2);

    }

    @Test
    public void BankRepository_FindByCustomerName_ReturnsTheAccount() {
        BankAccount account = BankAccount.builder().customerName("Mary H").balance(500).build();
        bankRepository.save(account);
        BankAccount accountReturn = bankRepository.findByCustomerName(account.getCustomerName()).get();
        System.out.println("I found the account, it is: " + accountReturn);
        Assertions.assertThat(accountReturn).isNotNull();
    }

    @Test
    public void BankRepository_UpdateAccount_ReturnsTheAccountUpdated() {
        BankAccount account = BankAccount.builder().customerName("Mary H").balance(500).build();
        bankRepository.save(account);
        BankAccount accountSaved = bankRepository.findById(account.getId()).get();
        accountSaved.setCustomerName("Jonny");
        accountSaved.setBalance(900);
        BankAccount updatedAccount = bankRepository.save(accountSaved);
        Assertions.assertThat(updatedAccount.getCustomerName()).isEqualTo("Jonny");
    }


}
