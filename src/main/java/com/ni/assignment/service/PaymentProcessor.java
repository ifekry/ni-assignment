package com.ni.assignment.service;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ni.assignment.model.PaymentProcessorRequest;
import com.ni.assignment.model.PaymentProcessorResponse;
import com.ni.assignment.model.Transaction;

public class PaymentProcessor {
	
	Logger log = LoggerFactory.getLogger(PaymentProcessor.class);
	
	/* Dummy function to simulate Payment Processor response */
	public PaymentProcessorResponse processTransaction(PaymentProcessorRequest transactionRequest) {
		try {
			log.debug("Processing transaction");
			PaymentProcessorResponse transactionResponse = new PaymentProcessorResponse();
			transactionResponse.setRespCode("00"); /* Approved */
			
			/* Generate Random Auth Number (6 digits) */
			Random rand = new Random();
			long randomValue = rand.nextInt(1000000);
			String authNumber = StringUtils.leftPad(Long.toString(randomValue), 6);
			transactionResponse.setAuthNumber(authNumber);
			
			log.debug("Transaction processed successfully");
			return transactionResponse;			
		} catch (Exception e) {
			/* Unable to process */
			log.error("Unable to process transaction [Error: " + e.getMessage() + "]");
			return new PaymentProcessorResponse("X2", null);
		}
	}

}
