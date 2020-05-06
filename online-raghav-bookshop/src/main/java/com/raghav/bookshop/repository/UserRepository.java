package com.raghav.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raghav.bookshop.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);
	
	public User findByEmailVerificationToken(String token);

	public boolean existsByEmail(String email);
	
}
