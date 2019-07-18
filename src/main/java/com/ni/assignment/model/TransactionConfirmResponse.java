package com.ni.assignment.model;

public class TransactionConfirmResponse {
	
	private Long transactionId;
	private String respCode;
	
	/* Constructors */
	public TransactionConfirmResponse() {}
	public TransactionConfirmResponse(Long transactionId, String respCode) {
		this.transactionId = transactionId;
		this.respCode = respCode;
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
	

}
