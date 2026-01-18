package com.bank.service;

import com.bank.exception.AmountInvalidException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.UserNotFoundException;
import com.bank.transaction.Transaction;
import com.bank.transaction.TransactionRepository;
import com.bank.transaction.TransactionType;
import com.bank.user.User;
import com.bank.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getBalance(String username) {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return u.getBalance();
    }

    @Transactional
    public BigDecimal deposit(String username, BigDecimal amount) {
        validateAmount(amount);

        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        u.setBalance(u.getBalance().add(amount));
        userRepository.save(u);

        // Transaction record deposit
        transactionRepository.save(Transaction.deposit(username, amount, u.getBalance()));

        return u.getBalance();
    }

    @Transactional
    public BigDecimal withdraw(String username, BigDecimal amount) {
        validateAmount(amount);

        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (u.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        u.setBalance(u.getBalance().subtract(amount));
        userRepository.save(u);

        // Transaction record withdraw
        transactionRepository.save(Transaction.withdraw(username, amount, u.getBalance()));

        return u.getBalance();
    }

    @Transactional
    public void transfer(String fromUsername, String toUsername, BigDecimal amount) {
        validateAmount(amount);

        if (fromUsername.equals(toUsername)) {
            throw new AmountInvalidException("Cannot transfer to yourself");
        }

        User from = userRepository.findByUsername(fromUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User to = userRepository.findByUsername(toUsername)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // renew balance
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        userRepository.save(from);
        userRepository.save(to);

        // Transaction record In & Out 交易紀錄：轉出 & 轉入（各一筆，查詢時更清晰）
        transactionRepository.save(Transaction.transferOut(fromUsername, toUsername, amount, from.getBalance()));
        transactionRepository.save(Transaction.transferIn(toUsername, fromUsername, amount, to.getBalance()));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) throw new AmountInvalidException("Amount is required");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new AmountInvalidException("Amount must be > 0");
        if (amount.scale() > 2) throw new AmountInvalidException("Amount scale must be <= 2");
    }
}
