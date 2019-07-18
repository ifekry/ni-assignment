package com.ni.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class PaymentApp extends SpringBootServletInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(PaymentApp.class);
	
	/* Application entry point */
	public static void main(String[] args) {
		log.debug("Starting Application");
	    System.setProperty("jasypt.encryptor.password", "P@ssw0rd#900"); /* Configuration File Password */
		SpringApplication.run(PaymentApp.class, args);
		log.info("Application Started");
	}

}
