package com.ni.assignment.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ni.assignment.model.Merchant;
import com.ni.assignment.model.Transaction;

public class TransactionValidationTest {

	TransactionValidation transactionValidation = new TransactionValidation();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testValidateTransaction() {
		Merchant merch = new Merchant();
		merch.setMerchantId(new Long(1234));
		
		Transaction trx = new Transaction();
		trx.setMerchant(merch);
		trx.setAmount(new Float(90.95));
		trx.setStatus(Transaction.TransactionStatus.Initiated);

		
		String cardNumber = "4931383493529024";
		Float amount = new Float(90.95);
		String cvv = "123";
		String expiryDate = "2205";
		
		assertEquals(transactionValidation.ValidateTransaction(trx, merch, cardNumber, amount, cvv, expiryDate), TransactionValidation.transactionValidationResult.Valid);
		
	}
	
	@Test
	public void testIsValidCardNumber() {
		assertEquals(transactionValidation.isValidCardNumber("4931383493529024"), true);
		assertEquals(transactionValidation.isValidCardNumber("1234567890123456"), false);
	}

}
