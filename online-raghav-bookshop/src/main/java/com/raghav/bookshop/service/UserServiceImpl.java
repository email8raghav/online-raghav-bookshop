package com.raghav.bookshop.service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.raghav.bookshop.entity.Authority;
import com.raghav.bookshop.entity.User;
import com.raghav.bookshop.repository.UserRepository;
import com.raghav.bookshop.requestModel.SignUpUserRequestModel;
import com.raghav.bookshop.requestModel.UpdateUserRequestModel;
import com.raghav.bookshop.utility.DateParser;



@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	private EmailVerificationService emailVerificationService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		return user;
	}

	@Override
	public User createNewUser(SignUpUserRequestModel signUpUserRequestModel) {
		 
		ModelMapper modelmapper  = new ModelMapper();
		User user = modelmapper.map(signUpUserRequestModel, User.class);
		
		user.setPublicUserId(UUID.randomUUID().toString());
		
		user.setEncryptedPassword(bcryptPasswordEncoder.encode(signUpUserRequestModel.getPassword()));
		
		user.setDateOfBirth(DateParser.localDateParser(signUpUserRequestModel.getDob()));
		
		user.setEmailIsVerified(false);
		
		String token = emailVerificationService.generateEmailVerificationToken(user.getPublicUserId());
		
		user.setEmailVerificationToken(token);
		
		Authority userAuthority = new Authority();
		userAuthority.setAuthority("ROLE_USER");
		userAuthority.setUser(user);
		
		Set<Authority> authorities = new HashSet<>();
		authorities.add(userAuthority);
		
		user.setAuthorities(authorities);
		
		return userRepository.save(user);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User getUserByToken(String token) {
		return userRepository.findByEmailVerificationToken(token);
	}

	@Override
	public void updateUserEmailVerificationStatus(User user) {
		userRepository.save(user);
		
	}

	@Override
	public boolean isEmailExits(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User updatePassword(String newPassword, User user) {
		user.setEncryptedPassword(bcryptPasswordEncoder.encode(newPassword));
		return userRepository.save(user);
	}

	@Override
	public User updateUser(UpdateUserRequestModel updateUserRequestModel) {
		//return userRepository.save(updateUserRequestModel);
		return null;
	}

	
}
