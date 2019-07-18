package com.ni.assignment.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.ni.assignment.dao.MerchantDAO;
import com.ni.assignment.model.Merchant;

public class MerchantValidationTest {
	
	MerchantDAO merchantDAO = new MerchantDAO();
	MerchantValidation merchantValidation = new MerchantValidation();
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testValidateMerchant() {
		Merchant merch = new Merchant();
		merch.setMerchantId(new Long(1234));
		merch.setMerchantSecretCode("123456");
		
		assertEquals(merchantValidation.validateMerchant(merch, "123456"), MerchantValidation.merchantValidationResult.Valid);
		assertEquals(merchantValidation.validateMerchant(merch, "1234567"), MerchantValidation.merchantValidationResult.InvalidSecretCode);
	}

}
