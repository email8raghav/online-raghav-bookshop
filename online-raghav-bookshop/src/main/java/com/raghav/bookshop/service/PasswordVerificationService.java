package com.raghav.bookshop.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.raghav.bookshop.entity.PasswordResetToken;
import com.raghav.bookshop.entity.User;
import com.raghav.bookshop.exception.InvalidTokenException;
import com.raghav.bookshop.exception.UserNotFoundException;
import com.raghav.bookshop.repository.PasswordResetRepository;
import com.raghav.bookshop.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class PasswordVerificationService {

	@Autowired
	private Environment env;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordResetRepository passwordResetRepository;

	@Transactional
	public String generatePsswordResetToken(String email) {

		boolean status = userRepository.existsByEmail(email);

		if (status) {

			User user = userRepository.findByEmail(email);
			/*
			 * Preparing password reset token
			 */
			String token = Jwts.builder().setSubject(user.getPublicUserId())
					.setExpiration(new Date(
							System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
					.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();

			/*
			 * Preparing entity class to persist into db
			 * 
			 */
			PasswordResetToken passwordResetToken = new PasswordResetToken(token);
			passwordResetToken.setUserEntity(user);
			/*
			 * Persisting to db
			 * 
			 */
			passwordResetRepository.save(passwordResetToken);

			return token;

		} else {

			throw new UserNotFoundException("We are Sorry !!! This Email is not Found in our database !!!");
		}
	}

	public boolean isExpired(String token) {

		Claims claims = Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(token).getBody();

		Date tokenExpirationDate = claims.getExpiration();
		Date todayDate = new Date();
		return tokenExpirationDate.before(todayDate);
	}

	@Transactional
	public User verifyToken(String token) {

		PasswordResetToken tokenEntity = passwordResetRepository.findByToken(token);
		if (tokenEntity != null && !isExpired(tokenEntity.getToken())) {
			User userEntity = tokenEntity.getUserEntity();
			return userEntity;
		} else
			throw new InvalidTokenException("Invalid Token !!! Please Try again !!!");

	}


}
