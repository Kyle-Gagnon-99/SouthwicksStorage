package com.southwicksstorage.southwicksstorage.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Component
public class DataLoader implements ApplicationRunner {

	private UserDao userRepo;
	private PasswordEncoder bCryptPasswordEncoder;
	
	private static final String DEFAULT_PASSWORD = Constants.DEFAULT_PASSWORD;
	
	@Autowired
	public DataLoader(UserDao userRepo, PasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if(!userRepo.existsByUsername("kgagnon")) {
			userRepo.save(new UserModelEntity("Kyle", "Gagnon", "kgagnon", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), Roles.MANAGER));
		}
		
		if(!userRepo.existsByUsername("rhorn")) {
			userRepo.save(new UserModelEntity("Ryan", "Horn", "rhorn", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), Roles.MANAGER));
		}
		
		if(!userRepo.existsByUsername("lplumb")) {
			userRepo.save(new UserModelEntity("Lauren", "Plumb", "lplumb", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), Roles.TEAM_MEMBER));
		}
		
	}

	
	
}
