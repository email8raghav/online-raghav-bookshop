package com.raghav.bookshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.raghav.bookshop.entity.User;
import com.raghav.bookshop.requestModel.SignUpUserRequestModel;
import com.raghav.bookshop.requestModel.UpdateUserRequestModel;



public interface UserService extends UserDetailsService {
	
	public User createNewUser(SignUpUserRequestModel signUpUserRequestModel);
	
	public User updateUser(UpdateUserRequestModel updateUserRequestModel);
	
	public User getUserByEmail(String email);

	public User getUserByToken(String token);

	public void updateUserEmailVerificationStatus(User user);
	
	public boolean isEmailExits(String email);
	
	public User updatePassword(String newPassword, User user);

}
