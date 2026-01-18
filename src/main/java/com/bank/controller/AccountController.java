package com.bank.controller;

import com.bank.account.dto.AmountRequest;
import com.bank.account.dto.TransferRequest;
import com.bank.service.AccountService;
import com.bank.transaction.Transaction;
import com.bank.transaction.TransactionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    public AccountController(AccountService accountService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    // balance query (after login)查餘額（必須登入）
    @GetMapping("/balance")
    public Map<String, Object> balance(Authentication auth) {
        String username = auth.getName();
        BigDecimal balance = accountService.getBalance(username);
        return Map.of("username", username, "balance", balance);
    }

    // deposit(after login)存錢（必須登入）
    @PostMapping("/deposit")
    public Map<String, Object> deposit(@RequestBody AmountRequest req, Authentication auth) {
        String username = auth.getName();
        BigDecimal newBalance = accountService.deposit(username, req.getAmount());
        return Map.of("username", username, "balance", newBalance);
    }

    // withdraw(after login)提錢（必須登入）
    @PostMapping("/withdraw")
    public Map<String, Object> withdraw(@RequestBody AmountRequest req, Authentication auth) {
        String username = auth.getName();
        BigDecimal newBalance = accountService.withdraw(username, req.getAmount());
        return Map.of("username", username, "balance", newBalance);
    }

    // transfer(after login)轉帳（必須登入）
    @PostMapping("/transfer")
    public Map<String, Object> transfer(@RequestBody TransferRequest req, Authentication auth) {
        String from = auth.getName();
        accountService.transfer(from, req.getToUsername(), req.getAmount());
        return Map.of("from", from, "to", req.getToUsername(), "amount", req.getAmount(), "status", "ok");
    }

    // transaction history(afterlogin)交易記錄（必須登入）
    @GetMapping("/transactions")
    public List<Transaction> transactions(Authentication auth) {
        String username = auth.getName();
        return transactionRepository.findByOwnerUsernameOrderByCreatedAtDesc(username);
    }
}
