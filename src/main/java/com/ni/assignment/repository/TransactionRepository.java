package com.ni.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ni.assignment.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}

