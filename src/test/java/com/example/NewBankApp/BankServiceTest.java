package com.example.NewBankApp;

import com.example.NewBankApp.entity.BankAccount;
import com.example.NewBankApp.repo.BankRepository;
import com.example.NewBankApp.service.BankService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {
    @Mock
    BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;
    @BeforeEach
    void setup(){
        this.bankRepository= mock(BankRepository.class);
        this.bankService=new BankService(bankRepository);
    }

    @Test
    public void BankService_saveAccount_ReturnSavedAccount() {
        //Given/Arrange:I mock the behavior of the bankRepository.save()
        // method to return saved account (account1).
        BankAccount account = BankAccount.builder().id(1).customerName(" Mary J").balance(500).build();
        when(bankRepository.save(Mockito.any(BankAccount.class))).thenReturn(account);

        //When/Act: Invoke the method being tested with a sample BankAccount
        BankAccount savedAccount = bankService.saveBankAccount(account);
        //Then/Assert: that the account it is saved
        Assertions.assertThat(savedAccount.getId()).isEqualTo(account.getId());
        Assertions.assertThat(savedAccount.getCustomerName()).isEqualTo(account.getCustomerName());
        Assertions.assertThat(savedAccount.getBalance()).isEqualTo(account.getBalance());
        Assertions.assertThat(savedAccount).isNotNull();
        System.out.println("Test is passing, I found the next: "+ account);

    }

    @Test
    public void BankService_getAllAccounts_ReturnAllAccounts() {
        //Given/Arrange:I mock the behavior of the bankRepository.findAll()
        // method to return a list of two bank accounts (account1 and account2).
        BankAccount account1 = BankAccount.builder().id(1).customerName("Mary H").balance(500).build();
        BankAccount account2 = BankAccount.builder().id(2).customerName("Jonny L").balance(200).build();
        when(bankRepository.findAll()).thenReturn(Arrays.asList(account1,account2));

        //When/Act: I call the method to be tested getAllAccounts() of the bankService
        List<BankAccount> accountsList=bankService.getAllAccounts();
        //Then/Assert: that the accountsList returned by the getAllAccounts()
        // method contains account1 and account2
        Assertions.assertThat(accountsList).contains(account1, account2);
        System.out.println("Test is passing, I found the next accounts: "+ accountsList);

    }
    @Test
    public void testWithdrawalPositiveAmount() {
        // Given:I Mock the behavior of the bankRepository.findById method
        BankAccount account = BankAccount.builder().id(1).customerName("Mary J").balance(1000).build();
        Optional<BankAccount> optionalAccount = Optional.of(account);
        when(bankRepository.findById(anyLong())).thenReturn(optionalAccount);

        // When: Call the method being tested (withdraw) from the bankService
        int withdrawalAmount = 500;
        bankService.withdraw(withdrawalAmount, account.getId());

        // Then: Assert that the balance has been updated correctly
        assertEquals(500, account.getBalance());
    }
    @Test
    public void testDepositPositiveAmount() {
        // Given: I Mock the behavior of the bankRepository.findById method
        BankAccount account = BankAccount.builder().id(1).customerName("Mary J").balance(1000).build();
        Optional<BankAccount> optionalAccount = Optional.of(account);
        when(bankRepository.findById(anyLong())).thenReturn(optionalAccount);

        // When: Call the method being tested (deposit) from the bankService
        int depositAmount = 500;
        bankService.deposit(depositAmount, account.getId());

        // Then: Assert that the balance has been updated correctly
        assertEquals(1500, account.getBalance());
    }
}
