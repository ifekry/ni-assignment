package com.ni.assignment.model;

public class PaymentProcessorResponse {
	
	private String respCode;
	private String authNumber; /* Authorization Number / Approval Number */
	
	/* Constructors */
	public PaymentProcessorResponse() {}
	public PaymentProcessorResponse(String respCode, String authNumber) {
		this.respCode = respCode;
		this.authNumber = authNumber;
	}
	
	/* Setters/Getters */
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
