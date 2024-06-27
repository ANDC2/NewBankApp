package com.example.NewBankApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private int balance;


    public int increase(int amount) {
        return balance = balance += amount;

    }

    public int decrease(int amount) {
        return balance = balance -= amount;

    }
}
