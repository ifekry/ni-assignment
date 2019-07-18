package com.ni.assignment.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ni.assignment.dao.MerchantDAO;
import com.ni.assignment.dao.TransactionDAO;
import com.ni.assignment.model.Merchant;
import com.ni.assignment.model.PaymentProcessorRequest;
import com.ni.assignment.model.PaymentProcessorResponse;
import com.ni.assignment.model.Transaction;
import com.ni.assignment.model.TransactionConfirmResponse;
import com.ni.assignment.model.TransactionInitiateResponse;
import com.ni.assignment.model.TransactionSubmitResponse;
import com.ni.assignment.service.MerchantValidation;
import com.ni.assignment.service.PaymentProcessor;
import com.ni.assignment.service.TransactionCryptography;
import com.ni.assignment.service.TransactionValidation;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableEncryptableProperties
@RestController
public class TransactionController {
	
	Logger log = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	TransactionDAO transactionDAO;
	
	@Autowired
	MerchantDAO merchantDAO;
	
	@Value("${encKey}")
    private String masterEncryptionKey; /* Master key used to encrypt sensitive information */
	
	private TransactionCryptography trxCrypt = new TransactionCryptography();
		
	/* To initiate a new transaction */
	@PostMapping("/initiate")
	public TransactionInitiateResponse initiateTransaction(@RequestParam(value="merchantId") Long merchantId, @RequestParam(value="merchantSecretCode") String merchantSecretCode) {
		try {
				log.info("Initiating new transaction [MerchantId: " + merchantId + "]" );
				Merchant merch = merchantDAO.getMerchantById(merchantId);
				if (merch == null) {
					/* Invalid Merchant Id*/
					log.info("Transaction initiation fialed - Invalid Merchant Id");
					return new TransactionInitiateResponse("M1", "Invalid Merchant Id", null, null); 
				}
				
				/* Generate Random Key */
				log.debug("Generating a new transaction encryption key");
				String encryptedKey = trxCrypt.GenerateRandomKey(masterEncryptionKey, merch.getMerchantEncryptionKey());
				log.debug("Transaction encryption key generated");
				
				/* Validate request */
				log.debug("Validating request");
				MerchantValidation merchValidation = new MerchantValidation();
				MerchantValidation.merchantValidationResult validationResult = merchValidation.validateMerchant(merch, merchantSecretCode);
				switch (validationResult) {
					case InvalidMerchantId: {
						/* Invalid Merchant Id */
						log.info("Transaction initiation fialed - Invalid Merchant Id");
						return new TransactionInitiateResponse("M1", "Invalid Merchant Id", null, null); 
					}
					case InvalidSecretCode: {
						/* Invalid Merchant Secret Code */
						log.info("Transaction initiation fialed - Invalid Merchant Secret Code"); 
						return new TransactionInitiateResponse("M1", "Invalid Merchant Secret Code", null, null);
					}
					case Valid: {
						/* Validation passed */
						log.debug("Validation passed successfully");
						break;
					}
				}
	
				/* Create new transaction */
				log.debug("Creating a new transaction");
				Transaction trx = new Transaction(merch, encryptedKey);
				trx = transactionDAO.save(trx);
				
				/* return transaction id & transaction key */
				return new TransactionInitiateResponse("00", "Success", Long.toString(trx.getId()), encryptedKey);			
		} catch (Exception e) {
			/* Error - Unable to initiate transaction */
			log.error("Unable to initiate transaction [" + e.getMessage() + "]");
			return new TransactionInitiateResponse("X2", "Failed - Unable to initiate transaction", null, null);
		}
	}
	
	/* To submit a transaction */
	@PostMapping("/submit")
	public TransactionSubmitResponse submitTransaction(@RequestParam(value="transactionId") Long transactionId, @RequestParam(value="merchantId") Long merchantId, @RequestParam(value="cardNumber") String cardNumber, @RequestParam(value="cvv2") String cvv2, @RequestParam(value="expiryDate") String expiryDate, @RequestParam(value="transactionAmount") Float transactionAmount) {
		log.info("Submitting transaction [TransactionId: " + transactionId + "]");
		/* Validate Request */
		Transaction trx = transactionDAO.getTransactionById(transactionId);
		if (trx == null) {
			/* Invalid Transaction Id */
			log.info("Transaction submission failed - Invalid Transaction Id");
			return new TransactionSubmitResponse(transactionId, "C1", null); 
		}
		Merchant merch = merchantDAO.getMerchantById(merchantId);
		if (merch == null) {
			/* Invalid Merchant Id */
			log.info("Transaction submission failed - Invalid Merchant Id");
			return new TransactionSubmitResponse(transactionId, "M1", null); 
		}
		if (trx.getEncryptedTransctionKey() == null) {
			/* Invalid Transaction Encryption Key */
			log.info("Transaction submission failed - Invalid Transaction Encryption key");
			return new TransactionSubmitResponse(transactionId, "C1", null); 			
		}
		String clearCardNumber = "";
		String clearCVV = "";
		/* Decrypt Card Number & CVV */
		log.debug("Decrypting card number and CVV");
		try {
			clearCardNumber = this.trxCrypt.DecryptCardNumber(cardNumber, masterEncryptionKey,  merch.getMerchantEncryptionKey(), trx.getEncryptedTransctionKey());
			clearCVV = this.trxCrypt.DecryptCVV(cvv2, masterEncryptionKey,  merch.getMerchantEncryptionKey(), trx.getEncryptedTransctionKey());
			log.debug("Card number and CVV Decryption successful");
		} catch (Exception e) {
			 /* Cryptographic Error */
			log.warn("Unable to decrypt card number and/or CVV - Cryptographic failure - Check encryption keys");
			return new TransactionSubmitResponse(transactionId, "X1", null);
		}
		if (clearCardNumber.equals("") || clearCVV.equals("")){
			/* Cryptographic Failure */
			log.warn("Transaction submission failed - Card Number and/or CVV not decrypted");
			return new TransactionSubmitResponse(transactionId, "X1", null); 
		}
		log.debug("Validating transaction details");
		TransactionValidation trxValidation = new TransactionValidation();
		TransactionValidation.transactionValidationResult validationResult = trxValidation.ValidateTransaction(trx, merch, clearCardNumber, transactionAmount, clearCVV, expiryDate);
		switch(validationResult) {
			case InvalidTransactionId: {
				/* Invalid Transaction Id */
				log.info("Transaction submission failed - Invalid Transaction Id");
				return new TransactionSubmitResponse(transactionId, "C1", null); 
			}
			case InvalidCardNumber: {		
				/* Invalid Card Number */
				log.info("Transaction submission failed - Invalid Card Number");
				return new TransactionSubmitResponse(transactionId, "C2", null); 
			}
			case InvalidCVV: {
				/* Invalid Card Verification Value */
				log.info("Transaction submission failed - Invalid Card Verification Value");
				return new TransactionSubmitResponse(transactionId, "C3", null); 
			}
			case InvalidAmount: {
				/* Invalid Amount */
				log.info("Transaction submission failed - Invalid Amount");
				return new TransactionSubmitResponse(transactionId, "C4", null);
			}
			case InvalidMerchantId: {
				/* Invalid Merchant */
				log.info("Transaction submission failed - Invalid Merchant Id");
				return new TransactionSubmitResponse(transactionId, "M1", null); 
			}
			case TransactionAlreadySubmitted: {
				/* Transaction Already Submitted */
				log.info("Transaction submission failed - Transaction Already Submitted or not in initial status");
				return new TransactionSubmitResponse(transactionId, "C5", null); 
			}
			case TransactionNotInitiatedBySameMerchant: {
				/* Transaction not initiated by same merchant */
				log.info("Transaction submission failed - Transaction not initiated by the same merchant");
				return new TransactionSubmitResponse(transactionId, "C6", null); 
			}
			case InvalidExpiryDate: {
				/* Invalid Expiry Date */
				log.info("Transaction submission failed - Invalid Expiry Date");
				return new TransactionSubmitResponse(transactionId, "C7", null); 
			}
			case Valid: {
				/* Transaction validation passed successfully */
				log.debug("Transaction Validation Passed Successfully");
				break;
			}
		}
	
		/* Update Transaction Data */
		log.debug("Updating transaction data in the database");
		try {
			trx.setMerchant(merch);
			trx.setCardNumber(clearCardNumber); /* Card number is masked before storing in database */
			trx.setAmount(transactionAmount);
			trx.setCurrencyCode(merch.getMerchantCurrencyCode());
			trx.setRequestDateTime(new Date());
			trx.setStatus(Transaction.TransactionStatus.Submitted); /* Transaction Submitted */
			transactionDAO.save(trx);
			log.debug("Transaction data saved successfully in the database");
		} catch (Exception e) {
			log.error("Unable to save transaction data in the database - " + e.getMessage());
			log.warn("Transaction not submitted due to the previous error [Transaction ID: " + transactionId + "]");
			return new TransactionSubmitResponse(transactionId, "X2", null); 
		}
		
		/* Send transaction to payment processor for processing */
		log.debug("Sending transaction to payment processor");
		PaymentProcessor paymentProcessor = new PaymentProcessor();
		String stan = StringUtils.leftPad(StringUtils.right(Long.toString(trx.getId()), 6), 6, "0"); /* System Trace Audit Number (6 digits) */
		PaymentProcessorRequest trxRequest = new PaymentProcessorRequest(clearCardNumber, Long.toString(merchantId), stan, clearCVV, expiryDate, "00");
		PaymentProcessorResponse trxResponse = paymentProcessor.processTransaction(trxRequest);
		if (trxResponse == null) {
			/* No response received from Payment Processor */
			log.warn("No response from Payment Processor");
			trx.setStatus(Transaction.TransactionStatus.Failed); /* Failed */
			trx.setEncryptedTransctionKey(""); /* Clear transaction encryption key */
			transactionDAO.save(trx);
			log.info("Transaction Processing Failed [Transaction ID: " + transactionId + "]");
			return new TransactionSubmitResponse(transactionId, "F1", null);
		}
		
		log.debug("Received response from payment processor, updating data in transaction table");
		/* Update returned data in transactions table */
		try {
			trx.setStatus(Transaction.TransactionStatus.Processed); /* Processed */
			trx.setRespCode(trxResponse.getRespCode());
			trx.setAuthNumber(trxResponse.getAuthNumber());
			trx.setResponseDateTime(new Date());
			trx.setEncryptedTransctionKey(""); /* Clear transaction encryption key */
			transactionDAO.save(trx);
			log.debug("Transaction data updated successfully");
			log.info("Transaction processed successfully [Transaction ID: " + transactionId + "]");
			return new TransactionSubmitResponse(transactionId, trxResponse.getRespCode(), trxResponse.getAuthNumber());				
		} catch (Exception e) {
			log.error("Unable to save transaction data [Error: " + e.getMessage() + "]");
			log.warn("Transaction response not saved in transaction table");
			return new TransactionSubmitResponse(transactionId, "X2", null);								
		}
	}
	
	/* To confirm a transaction */
	@PostMapping("/confirm")
	public TransactionConfirmResponse confrimTransaction(@RequestParam(value="transactionId") Long transactionId) {
		log.info("Confirming transaction [TransactionId: " + transactionId + "]");
		try {
			Transaction trx = transactionDAO.getTransactionById(transactionId);
			log.debug("Validating Request");
			/* Validate Request */
			if (trx == null) {
				/* Transaction not found */
				return new TransactionConfirmResponse(transactionId, "C1"); 
			} else if (trx.getStatus() != Transaction.TransactionStatus.Processed) {
				/* Transaction not processed */
				return new TransactionConfirmResponse(transactionId, "C8"); 	
			}
			log.debug("Validation passed, updating transaction status");
			/* Update transaction status */
			trx.setStatus(Transaction.TransactionStatus.Completed); /* Confirmed and Completed */
			transactionDAO.save(trx);
			/* Transaction confirmed */
			log.info("Transaction confirmed successfully [Transaction ID: " + transactionId + "]");
			return new TransactionConfirmResponse(transactionId, "00"); 
		} catch (Exception e) {
			/* Unable to confirm transaction */
			log.error("Unable to confirm transaction [Error: " + e.getMessage() + "]");
			log.warn("Transaction not confirmed due to the previous error [Transaction ID: " + transactionId + "]");
			return new TransactionConfirmResponse(transactionId, "X2"); 
		}

	}

}
