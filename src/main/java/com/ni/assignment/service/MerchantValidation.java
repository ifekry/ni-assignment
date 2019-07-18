package com.ni.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ni.assignment.model.Merchant;
import com.ni.assignment.model.Transaction;

public class MerchantValidation {
	
	Logger log = LoggerFactory.getLogger(MerchantValidation.class);
	
	public enum merchantValidationResult {
		Valid,
		InvalidMerchantId,
		InvalidSecretCode
	}
	
	/* To validate merchant details */
	public merchantValidationResult validateMerchant(Merchant merch, String merchantSecretCode) {
		try {
			log.debug("Validating Merchant");
			if (merch == null) {
				log.debug("Merchant is null - Invalid Merchant");
				return merchantValidationResult.InvalidMerchantId;
			} else if (merch.getMerchantSecretCode() == null || merchantSecretCode == null) {
				log.debug("Invalid Secret Code");
				return merchantValidationResult.InvalidSecretCode;
			} else if (!merch.getMerchantSecretCode().equals(merchantSecretCode)) {
				log.debug("Incorrect Secret Code");
				return merchantValidationResult.InvalidSecretCode;
			} else {
				log.debug("Merchant validation passed successfully");
				return merchantValidationResult.Valid;
			}
		} catch (NullPointerException e) {
			/* Unable to validate merchant */
			log.error("Unable to validate merchant [Error: " + e.getMessage() + "]");
			return merchantValidationResult.InvalidMerchantId;
		}
	}
}
