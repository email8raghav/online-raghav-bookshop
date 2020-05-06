package com.raghav.bookshop.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.raghav.bookshop.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class EmailVerificationService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;

	public boolean verifyEmail(String token) {
		User user = userService.getUserByToken(token);
		if (user != null) {
			Boolean isTokenExpired = isTokenExpired(token);
			if (isTokenExpired) {
				return false;
			}
			user.setEmailIsVerified(true);
			user.setEmailVerificationToken(null);
			userService.updateUserEmailVerificationStatus(user);
			return true;
		}

		return false;
	}

	private Boolean isTokenExpired(String token) {
		Claims claims = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
				.parseClaimsJws(token).getBody();
				
		Date tokenExpirationDate = claims.getExpiration();
		Date todayDate = new Date();
		return tokenExpirationDate.before(todayDate);
	
	}
	
	public String generateEmailVerificationToken(String publicUserId) {

		String token = Jwts.builder().setSubject(publicUserId)
				.setExpiration(
						new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();

		return token;
	}

}
