package com.ni.assignment.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionSubmitResponse {
	
	Logger log = LoggerFactory.getLogger(TransactionSubmitResponse.class);
	
	private Long transactionId;
	private String respCode;
	private String authNumber;
	
	/* Constructors */
	public TransactionSubmitResponse() {}
	public TransactionSubmitResponse(Long trxId, String rc, String authNum) {
		try {
			this.transactionId = trxId;
			this.respCode = rc;
			this.authNumber = authNum;			
		} catch (Exception e) {
			log.error("Unable to create TransactionSubmitResponse Object [Error: " + e.getMessage() + "]");
		}
	}
	
	/* Setters/Getters */
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getAuthNumber() {
		return authNumber;
	}
	public void setAuthNumber(String authNumber) {
		this.authNumber = authNumber;
	}

}
