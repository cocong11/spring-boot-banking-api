package com.bank.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOwnerUsernameOrderByCreatedAtDesc(String ownerUsername);
}

