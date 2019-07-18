package com.ni.assignment.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionInitiateResponse {
	
	Logger log = LoggerFactory.getLogger(TransactionInitiateResponse.class);
	
	private String respCode;
	private String respDescription;
	private String transactionUniqueId;
	private String transactionKey;
	
	/* Constructors */
	public TransactionInitiateResponse() {}
	public TransactionInitiateResponse(String respCode, String respDescription, String transactionUniqueId, String transactionKey) {
		try {
			this.respCode = respCode;
			this.respDescription = respDescription;
			this.transactionUniqueId = transactionUniqueId;
			this.transactionKey = transactionKey;			
		} catch (Exception e) {
			log.error("Unable to create TransactionInitiateResponse Object [Error: " + e.getMessage() + "]");
		}
	}
	
	/* Setters/Getters */
	public String getTransactionUniqueId() {
		return transactionUniqueId;
	}
	public void setTransactionUniqueId(String transactionUniqueId) {
		this.transactionUniqueId = transactionUniqueId;
	}
	public String getTransactionKey() {
		return transactionKey;
	}
	public void setTransactionKey(String transactionKey) {
		this.transactionKey = transactionKey;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDescription() {
		return respDescription;
	}
	public void setRespDescription(String respDescription) {
		this.respDescription = respDescription;
	}

}
