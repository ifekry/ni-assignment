package com.ni.assignment.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TransactionCryptographyTest {
	
	TransactionCryptography transactionCryptography = new TransactionCryptography();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDecryptCardNumber() {
		String clearCardNumber = "4931383493529024";
		String encryptedCardNumber = "B13955C119F04D9E87D37D88DB1527FFA0E716BD61FAAFC6";
		String masterEncryptionKey = "B0BCE9B398077320C79E0D1079B59B6B85E0207F1A5123BC";
		String merchantKey = "5FC233A7D06923D3054A25C9A67A2C6DF1A3363D76BE74F653D4030EDD99A207E5517CA1D9509831BFEAF7480FEF446C4B6E1AA3629AB395";
		String transactionKey = "41798AAFCB20DAFE9E70DB0216EDAD2BF916BFAA8FC793A94B8936D3BB3038C65601872EE5B79D79DC2BF4F9506E42EA4B6E1AA3629AB395";
		
		String decryptedCardNumber = transactionCryptography.DecryptCardNumber(encryptedCardNumber, masterEncryptionKey, merchantKey, transactionKey);
		assertEquals(clearCardNumber, decryptedCardNumber);
	}

	@Test
	public void testDecryptCVV() {
		String clearCVV = "123";
		String encryptedCVV = "B80856FB0EF1A0DB";
		String masterEncryptionKey = "B0BCE9B398077320C79E0D1079B59B6B85E0207F1A5123BC";
		String merchantKey = "5FC233A7D06923D3054A25C9A67A2C6DF1A3363D76BE74F653D4030EDD99A207E5517CA1D9509831BFEAF7480FEF446C4B6E1AA3629AB395";
		String transactionKey = "41798AAFCB20DAFE9E70DB0216EDAD2BF916BFAA8FC793A94B8936D3BB3038C65601872EE5B79D79DC2BF4F9506E42EA4B6E1AA3629AB395";
		
		String decryptedCVV = transactionCryptography.DecryptCardNumber(encryptedCVV, masterEncryptionKey, merchantKey, transactionKey);
		assertEquals(clearCVV, decryptedCVV);
	}

}
