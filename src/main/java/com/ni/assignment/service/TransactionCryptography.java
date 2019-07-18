package com.ni.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ni.assignment.model.Transaction;

public class TransactionCryptography {
	
	Logger log = LoggerFactory.getLogger(TransactionCryptography.class);
	
	/* To Decrypt Card Number */
	public String DecryptCardNumber(String encryptedCardNumber, String masterEncryptionKey, String merchantKey, String transactionKey) {
		try {
			log.debug("Decrypting card number");
			String clearMerchantKey = Cryptography.decrypt(Cryptography.GetKey(masterEncryptionKey), merchantKey);
			String transactionClearKey = Cryptography.decrypt(Cryptography.GetKey(clearMerchantKey), transactionKey);
			String clearCardNumber = Cryptography.decrypt(Cryptography.GetKey(transactionClearKey), encryptedCardNumber);
			return clearCardNumber;
		} catch (Exception e) {
			log.error("Cryptographic Error - Unable to decyrpt card number [Error: " + e.getMessage() + "]");
			return null;
		}
	}

	/* To Decrypt Card Verification Value */
	public String DecryptCVV(String encryptedCVV, String masterEncryptionKey, String merchantKey, String transactionKey) {
		try {
			log.debug("Decrypting card number");
			String clearMerchantKey = Cryptography.decrypt(Cryptography.GetKey(masterEncryptionKey), merchantKey);
			String transactionClearKey = Cryptography.decrypt(Cryptography.GetKey(clearMerchantKey), transactionKey);
			String clearCVV = Cryptography.decrypt(Cryptography.GetKey(transactionClearKey), encryptedCVV);
			return clearCVV;
		} catch (Exception e) {
			log.error("Cryptographic Error - Unable to decyrpt CVV [Error: " + e.getMessage() + "]");
			return null;
		}
	}
	
	/* To Generate Random Transaction key */
	public String GenerateRandomKey(String masterEncryptionKey, String merchantKey) {
		try {
			String randomClearKey = Cryptography.GenKey();
			String merchantClearKey = Cryptography.decrypt(Cryptography.GetKey(masterEncryptionKey), merchantKey);
			String encryptedKey = Cryptography.encrypt(Cryptography.GetKey(merchantClearKey), randomClearKey);
			return encryptedKey;
		} catch (Exception e) {
			log.error("Unable to generate transaction key [Error: " + e.getMessage() + "]");
			return null;
		}
	}

}
