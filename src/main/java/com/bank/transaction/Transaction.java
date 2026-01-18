package com.bank.transaction;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who owns this transaction (used to check "My Transaction History") 這筆交易屬於誰（用來查「我的交易記錄」）
    @Column(nullable = false)
    private String ownerUsername;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    // Transaction counterparty (deposit/withdrawa can be null, but transfers will require this field)
    // 交易對象（存款/提款可以為 null，轉帳則會填）
    private String counterpartyUsername;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    // balance after transaction 交易後餘額
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal balanceAfter;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public Transaction() {}

    // ===== factory methods=====
    public static Transaction deposit(String owner, BigDecimal amount, BigDecimal balanceAfter) {
        return build(owner, TransactionType.DEPOSIT, null, amount, balanceAfter);
    }

    public static Transaction withdraw(String owner, BigDecimal amount, BigDecimal balanceAfter) {
        return build(owner, TransactionType.WITHDRAW, null, amount, balanceAfter);
    }

    public static Transaction transferOut(String owner, String to, BigDecimal amount, BigDecimal balanceAfter) {
        return build(owner, TransactionType.TRANSFER_OUT, to, amount, balanceAfter);
    }

    public static Transaction transferIn(String owner, String from, BigDecimal amount, BigDecimal balanceAfter) {
        return build(owner, TransactionType.TRANSFER_IN, from, amount, balanceAfter);
    }

    private static Transaction build(String owner, TransactionType type, String counterparty,
                                     BigDecimal amount, BigDecimal balanceAfter) {
        Transaction t = new Transaction();
        t.ownerUsername = owner;
        t.type = type;
        t.counterpartyUsername = counterparty;
        t.amount = amount;
        t.balanceAfter = balanceAfter;
        t.createdAt = Instant.now();
        return t;
    }

    // ===== getters =====
    public Long getId() { return id; }
    public String getOwnerUsername() { return ownerUsername; }
    public TransactionType getType() { return type; }
    public String getCounterpartyUsername() { return counterpartyUsername; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public Instant getCreatedAt() { return createdAt; }
}

