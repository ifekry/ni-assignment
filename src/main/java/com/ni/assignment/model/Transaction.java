package com.ni.assignment.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="Transaction")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
	
	public enum TransactionStatus {
		Initiated,
		Submitted,
		Processed,
		Completed,
		Failed
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date requestDateTime;
	private String CardNumber;
	private String CurrencyCode;
	private Float Amount;
	private String encryptedTransctionKey;
	@ManyToOne
	@JoinColumn(name="merchantId")
	private Merchant Merchant;
	private TransactionStatus Status;
	private String AuthNumber;
	private Date responseDateTime;
	private String respCode;

	/* Constructors */
	public Transaction() {}
	public Transaction(Merchant merch, String encryptedKey) {
		this.Merchant = merch;
		this.encryptedTransctionKey = encryptedKey;
		this.Status = TransactionStatus.Initiated;
	}	
	
	/* Setters/Getters */
	public String getEncryptedTransctionKey() {
		return encryptedTransctionKey;
	}

	public void setEncryptedTransctionKey(String encryptedTransctionKey) {
		this.encryptedTransctionKey = encryptedTransctionKey;
	}
	
	public Merchant getMerchant() {
		return Merchant;
	}

	public void setMerchant(Merchant merchant) {
		Merchant = merchant;
	}

	public void setStatus(TransactionStatus status) {
		Status = status;
	}
	
	public TransactionStatus getStatus() {
		return Status;
	}
	
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRequestDateTime() {
		return requestDateTime;
	}

	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(String cardNumber) {
		/* Mask Card Number before storing */
		String maskedCardNumber = StringUtils.left(cardNumber, 4).concat("******").concat(StringUtils.right(cardNumber, 4));
		CardNumber = maskedCardNumber;			
	}

	public String getCurrencyCode() {
		return CurrencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}

	public Float getAmount() {
		return Amount;
	}

	public void setAmount(Float amount) {
		Amount = amount;
	}

	public String getAuthNumber() {
		return AuthNumber;
	}

	public void setAuthNumber(String authNumber) {
		AuthNumber = authNumber;
	}

	public Date getResponseDateTime() {
		return responseDateTime;
	}

	public void setResponseDateTime(Date responseDateTime) {
		this.responseDateTime = responseDateTime;
	}
}
