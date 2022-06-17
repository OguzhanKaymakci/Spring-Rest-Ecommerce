package com.works.repositories;

import com.works.entities.AdminChangePassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminChangePasswordRepository extends JpaRepository<AdminChangePassword, Long> {
}