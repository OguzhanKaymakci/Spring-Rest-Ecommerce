package com.works.repositories;

import com.works.entities.CustomerChangePassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerChangePasswordRepository extends JpaRepository<CustomerChangePassword, Long> {
}