package com.notes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.notes.entity.UserDtls;
import com.notes.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	public UserDetailsServiceImpl() {
		super();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDtls user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not exists");
		} else {
			CustomUserDetails customUserDetails = new CustomUserDetails(user);
			return customUserDetails;
		}
	}
}
