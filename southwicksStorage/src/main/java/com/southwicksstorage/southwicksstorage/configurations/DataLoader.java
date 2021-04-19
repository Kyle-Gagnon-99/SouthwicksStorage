package com.southwicksstorage.southwicksstorage.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Component
public class DataLoader implements ApplicationRunner {

	private UserDao userRepo;
	private PasswordEncoder bCryptPasswordEncoder;
	
	private static final String DEFAULT_PASSWORD = Constants.DEFAULT_PASSWORD;
	private static final String MANAGER_ROLE = Constants.MANAGER_ROLE;
	private static final String TEMA_MEMEBR_ROLE = Constants.TEAM_MEMBER_ROLE;
	
	@Autowired
	public DataLoader(UserDao userRepo, PasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		userRepo.save(new UserModelEntity("Kyle", "Gagnon", "kgagnon", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), MANAGER_ROLE));
		userRepo.save(new UserModelEntity("Ryan", "Horn", "rhorn", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), MANAGER_ROLE));
		userRepo.save(new UserModelEntity("Lauren", "Plumb", "lplumb", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), TEMA_MEMEBR_ROLE));
		
	}

	
	
}
