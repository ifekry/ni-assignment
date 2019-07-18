package com.ni.assignment.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ni.assignment.model.Transaction;
import com.ni.assignment.repository.TransactionRepository;

@Service
public class TransactionDAO {
	
	Logger log = LoggerFactory.getLogger(TransactionDAO.class);
	
	@Autowired
	TransactionRepository transactionRepository;
	
	/* To save a transaction */
	public Transaction save(Transaction trx) {
		log.debug("Saving transaction object to database");
		return transactionRepository.save(trx);
	}
	
	/* To retrieve a transaction by Id */
	public Transaction getTransactionById(Long id) {
		log.debug("Retrieving transaction from database by id [Transaction ID: " + id + "]");
		return transactionRepository.findOne(id);
	}

}
