package com.southwicksstorage.southwicksstorage.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	UserDao repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserModelEntity> loginDetails = repo.findByUsername(username);
		
		loginDetails.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
		
		return loginDetails.map(CustomUserDetails::new).get();
	}

	
	
}
