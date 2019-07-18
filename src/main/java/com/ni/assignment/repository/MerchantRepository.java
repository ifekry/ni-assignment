package com.ni.assignment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ni.assignment.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}