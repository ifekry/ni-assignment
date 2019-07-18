package com.ni.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ni.assignment.dao.MerchantDAO;
import com.ni.assignment.dao.TransactionDAO;
import com.ni.assignment.model.Merchant;
import com.ni.assignment.model.Transaction;

public class TransactionValidation {
	
	Logger log = LoggerFactory.getLogger(TransactionValidation.class);
	
	public enum transactionValidationResult {
		Valid,
		InvalidTransactionId,
		InvalidAmount,
		InvalidCardNumber,
		InvalidCVV,
		InvalidMerchantId,
		TransactionNotInitiatedBySameMerchant,
		TransactionAlreadySubmitted,
		InvalidExpiryDate
	}
	
	@Autowired
	TransactionDAO transactionDAO;
	@Autowired
	MerchantDAO merchantDAO;
	
	/* To Validate Transaction Details */
	public transactionValidationResult ValidateTransaction(Transaction trx, Merchant merch, String cardNumber, Float amount, String cvv, String expiryDate) {
		try {
			/* Validate Merchant */
			if (merch == null) {
				return transactionValidationResult.InvalidMerchantId;
			}
			/* Validate Transaction ID */
			else if (trx == null) {
				return transactionValidationResult.InvalidTransactionId;
			}
			/* Verify that transaction has been initiated by the same merchant */
			else if (!trx.getMerchant().getMerchantId().equals(merch.getMerchantId())) {
				System.err.println("Cached Merchant ID: " + merch.getMerchantId());
				System.err.println("Trx Merchant ID: " + trx.getMerchant().getMerchantId());
				return transactionValidationResult.TransactionNotInitiatedBySameMerchant;
			}
			/* Validate card number */
			else if (!isValidCardNumber(cardNumber) ) {
				return transactionValidationResult.InvalidCardNumber;
			}
			/* Validate CVV */
			else if (!isValidCVV(cvv)) {			
				return transactionValidationResult.InvalidCVV;
			}
			/* Validate Expiry Date */
			else if (!isValidExpiryDate(expiryDate)) {
				return transactionValidationResult.InvalidExpiryDate;
			}
			/* Validate Amount */
			else if (!isValidAmount(amount)) {
				return transactionValidationResult.InvalidAmount;
			}
			/* Validate Transaction Current Status */
			else if (trx.getStatus() != Transaction.TransactionStatus.Initiated) {
				return transactionValidationResult.TransactionAlreadySubmitted;
			}
			/* Validation passed */
			else {
				return transactionValidationResult.Valid;
			}
		} catch (Exception e) {
			log.error("Unable to validate transaction [Error: " + e.getMessage() + "]");
			return transactionValidationResult.InvalidTransactionId;
		}
	}
	
	/* To Validate Card Number */
	public boolean isValidCardNumber(String cardNumber) {
		/* Card Number has to be 13 to 19 digits and with valid check digit */
		try {
			log.debug("Validating Card Number");
			Long longCardNumber = Long.parseLong(cardNumber);
			 return (getSize(longCardNumber) >= 13 &&  
	               getSize(longCardNumber) <= 19) &&  
	              ((sumOfDoubleEvenPlace(longCardNumber) +  
	                sumOfOddPlace(longCardNumber)) % 10 == 0); 			
		} catch (Exception e) {
			log.error("Error validating card number [Error: " + e.getMessage() + "]");
			return false;
		}
	}
	
	/* To Validate CVV */
	private boolean isValidCVV(String cvv) {
		try {
			log.debug("Validating CVV");
			if (cvv != null && cvv.length() == 3 && Long.parseLong(cvv) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("Error validating CVV [Error: " + e.getMessage() + "]");
			return false;
		}
	}
	
	/* To Validate Expiry Date */
	private boolean isValidExpiryDate(String expiryDate) {
		try {
			log.debug("Validating Expiry Date");
			if (expiryDate == null) {
				return false;
			} else if (expiryDate.length() != 4) {
				return false;
			} else if (Integer.parseInt(expiryDate.substring(2, 4)) > 12 || Integer.parseInt(expiryDate.substring(2, 4)) < 1) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			log.error("Error validating Expiry Date [Error: " + e.getMessage() + "]");
			return false;
		}
	}
	
	/* To Validate Amount */
	private boolean isValidAmount(Float amount) {
		try {
			log.debug("Validating Amount");
			if (amount > 0 && amount <= 999999999 ) {
				return true;
			} else {
				return false;
			}			
		} catch (Exception e) {
			log.error("Error validating Expiry Date [Error: " + e.getMessage() + "]");
			return false;
		}
	}

	/* Used for card number check-digit validation */
	private int sumOfDoubleEvenPlace(long number) 
    { 
        int sum = 0; 
        String num = number + ""; 
        for (int i = getSize(number) - 2; i >= 0; i -= 2)  
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2); 
          
        return sum; 			
    } 
  
    /* Used for card number check-digit validation - Return this number if it is a single digit, otherwise, return the sum of the two digits */ 
	private static int getDigit(int number) 
    { 
        if (number < 9) 
            return number; 
        return number / 10 + number % 10; 
    } 
  
    /* Used for card number check-digit validation - Return sum of odd-place digits in number */ 
	private static int sumOfOddPlace(long number) 
    { 
        int sum = 0; 
        String num = number + ""; 
        for (int i = getSize(number) - 1; i >= 0; i -= 2)  
            sum += Integer.parseInt(num.charAt(i) + "");         
        return sum; 
    } 
  
    /* Used for card number check-digit validation - Return the number of digits in d */ 
	private static int getSize(long d) 
    { 
        String num = d + ""; 
        return num.length(); 
    } 
  
	

}
