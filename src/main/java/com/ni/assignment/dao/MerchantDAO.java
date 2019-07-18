package com.ni.assignment.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ni.assignment.controller.TransactionController;
import com.ni.assignment.model.Merchant;
import com.ni.assignment.repository.MerchantRepository;

@Service
public class MerchantDAO {
	
	Logger log = LoggerFactory.getLogger(MerchantDAO.class);
	
	@Autowired
	MerchantRepository merchantRepository;
	
	/* To retrieve a merchant by id */
	@Cacheable(value="merchant")
	public Merchant getMerchantById(Long id) {
		try {
			log.debug("Retreiving merchant object by id [Merchant ID: " + id + "]");
			return merchantRepository.findOne(id);
		} catch (NullPointerException e) {
			return null;
		}
	}

}
