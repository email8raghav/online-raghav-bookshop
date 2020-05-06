package com.raghav.bookshop;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.raghav.bookshop.entity.Authority;
import com.raghav.bookshop.entity.User;
import com.raghav.bookshop.repository.UserRepository;
import com.raghav.bookshop.utility.DateParser;


@Component
public class InitialUsersSetup {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {

		System.out.println("From Application ready event...");

		User adminUser = new User();
		adminUser.setFirstName("admin");
		adminUser.setLastName("admin");
		adminUser.setEmail("email8raghav@gmail.com");
		adminUser.setDateOfBirth(DateParser.localDateParser("1987-03-22"));
		adminUser.setEmailIsVerified(true);
		adminUser.setPublicUserId(UUID.randomUUID().toString());
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
		
		Authority adminAuthority = new Authority();
		adminAuthority.setAuthority("ROLE_ADMIN");
		adminAuthority.setUser(adminUser);
		
		Set<Authority> adminAuthorities = new HashSet<>();
		adminAuthorities.add(adminAuthority);
		
		adminUser.setAuthorities(adminAuthorities);

		userRepository.save(adminUser);
		
		User dummyUser = new User();
		dummyUser.setFirstName("raghav");
		dummyUser.setLastName("rai");
		dummyUser.setEmail("softraghavendra@live.com");
		dummyUser.setDateOfBirth(DateParser.localDateParser("1987-03-22"));
		dummyUser.setEmailIsVerified(true);
		dummyUser.setPublicUserId(UUID.randomUUID().toString());
		dummyUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
		
		Authority userAuthority = new Authority();
		userAuthority.setAuthority("ROLE_USER");
		userAuthority.setUser(dummyUser);
		
		Set<Authority> authorities = new HashSet<>();
		authorities.add(userAuthority);
		
		dummyUser.setAuthorities(authorities);
		
		userRepository.save(dummyUser);

	}

}
