package com.raghav.bookshop.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PasswordResetToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8936447051551464786L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String token; 
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public PasswordResetToken() {
	}

	public PasswordResetToken(String token){
		this.token = token;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUserEntity() {
		return user;
	}

	public void setUserEntity(User user) {
		this.user = user;
	}
	
	
}
