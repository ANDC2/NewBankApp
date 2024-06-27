package com.example.NewBankApp.controller;

import com.example.NewBankApp.entity.BankAccount;
import com.example.NewBankApp.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
@Controller
//@RestController
public class BankController {
    @Autowired
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/accounts")
    public List<BankAccount> getAllAccounts() {
        return bankService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<BankAccount> getProductById(@PathVariable Long id) {
        try {
            BankAccount account = bankService.getProductById(id).orElseThrow(() -> new RuntimeException("Account not found"));
            ;
            return ResponseEntity.ok(account);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/addAccount")
    public ResponseEntity<BankAccount> addAcc(@RequestBody BankAccount account) {
        BankAccount newAccount = bankService.saveBankAccount(account);
        return ResponseEntity.ok(newAccount);
    }
//    @PostMapping("/addAccount")
//    public String addAccount(@ModelAttribute("newAccount") BankAccount account) {
//        bankService.saveBankAccount(account);
//        return "redirect:/bankAccounts"; // Redirect to the bank accounts page
//    }


    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            bankService.deleteById(id);
            return ResponseEntity.ok("Account deleted successfully");
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/addDeposit")
    public ResponseEntity<String> addDeposit(@RequestBody BankAccount account) {
        try {
            BankAccount bankAccount = bankService.getProductById(account.getId()).orElseThrow(() -> new RuntimeException("Account not found"));
            bankService.deposit(account.getBalance(), bankAccount.getId());
            return ResponseEntity.ok("Deposit of " + account.getBalance() + " added to account with ID " + account.getId());
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> wihdraw(@RequestBody BankAccount account) {
        try {
            BankAccount bankAccount = bankService.getProductById(account.getId()).orElseThrow(() -> new RuntimeException("Account not found"));
            ;
            bankService.withdraw(account.getBalance(), bankAccount.getId());
            return ResponseEntity.ok("Withdraw of " + account.getBalance() + " from account with ID " + account.getId());
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping("/bankAccounts")
    public String getAccounts(Model model){
        List<BankAccount> accounts=bankService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts";
    }
}
