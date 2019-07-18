package com.ni.assignment.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ni.assignment.model.PaymentProcessorRequest;
import com.ni.assignment.model.PaymentProcessorResponse;

public class PaymentProcessorTest {
	
	
	
	PaymentProcessor paymentProcessor = new PaymentProcessor();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProcessTransaction() {
		PaymentProcessorRequest request = new PaymentProcessorRequest("4931383493529024","000000000001234","123456","123","2204","00");
		PaymentProcessorResponse response = paymentProcessor.processTransaction(request);
		assertEquals(response.getRespCode(),"00");
	}

}
