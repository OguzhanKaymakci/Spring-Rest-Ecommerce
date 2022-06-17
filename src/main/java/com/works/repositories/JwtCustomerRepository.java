package com.works.repositories;

import com.works.entities.JwtCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtCustomerRepository extends JpaRepository<JwtCustomer, Long> {
    Optional<JwtCustomer> findByEmailEqualsIgnoreCase(String email);



}