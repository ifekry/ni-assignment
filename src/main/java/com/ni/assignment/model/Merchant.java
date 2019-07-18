package com.ni.assignment.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="Merchant")
@EntityListeners(AuditingEntityListener.class)
public class Merchant {
	
	@Id
	private Long merchantId;
	private String merchantName;
	private String merchantSecretCode;
	private String merchantEncryptionKey;
	private String merchantCurrencyCode;
	
	/* Setters/Getters */
	public String getMerchantEncryptionKey() {
		return merchantEncryptionKey;
	}

	public void setMerchantEncryptionKey(String merchantEncryptionKey) {
		this.merchantEncryptionKey = merchantEncryptionKey;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantSecretCode() {
		return merchantSecretCode;
	}

	public void setMerchantSecretCode(String merchantSecretCode) {
		this.merchantSecretCode = merchantSecretCode;
	}

	public String getMerchantCurrencyCode() {
		return merchantCurrencyCode;
	}

	public void setMerchantCurrencyCode(String merchantCurrencyCode) {
		this.merchantCurrencyCode = merchantCurrencyCode;
	}
	

}
