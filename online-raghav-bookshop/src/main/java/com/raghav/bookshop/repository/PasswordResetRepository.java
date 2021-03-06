package com.raghav.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raghav.bookshop.entity.PasswordResetToken;


@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {

	public PasswordResetToken findByToken(String token);
}
