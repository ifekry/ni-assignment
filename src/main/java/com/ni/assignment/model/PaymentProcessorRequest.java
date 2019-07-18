package com.ni.assignment.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentProcessorRequest {
	
	Logger log = LoggerFactory.getLogger(PaymentProcessorRequest.class);
	
	private String stan; /* System Trace Audit Number */
	private String cardNumber; 
	private String merchantId;
	private String transactionType;
	private String cvv; /* Card Verification Value */
	private String expiryDate; /* Card Expiry Date */
	private Float transactionAmount;
	private String currencyCode;
	
	/* Constructors */
	public PaymentProcessorRequest() {}
	public PaymentProcessorRequest(String cardNumber, String merchantId, String stan, String cvv, String expiryDate, String transactionType) {
		try {
			this.cardNumber = cardNumber;
			this.merchantId = merchantId;
			this.stan = stan;
			this.cvv = cvv;
			this.expiryDate = expiryDate;
			this.transactionType = transactionType;
		} catch (Exception e) {
			log.error("Error creating PaymentProcessorRequest Object");
		}
	}

	/* Setters/Getters */
	public String getMerchantId() {
		return merchantId;
	}
	
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public String getStan() {
		return stan;
	}
	
	public void setStan(String stan) {
		this.stan = stan;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getCvv() {
		return cvv;
	}
	
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	public Float getTransactionAmount() {
		return transactionAmount;
	}
	
	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
}
