package com.southwicksstorage.southwicksstorage.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.NotificationMessages;
import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;
import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.repositories.NotificationDao;
import com.southwicksstorage.southwicksstorage.repositories.TypeOfStorageDao;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;
import com.southwicksstorage.southwicksstorage.repositories.VendorDao;

@Component
public class DataLoader implements ApplicationRunner {

	private UserDao userRepo;
	private NotificationDao notiRepo;
	private VendorDao vendorRepo;
	private TypeOfStorageDao storageRepo;
	private PasswordEncoder bCryptPasswordEncoder;
	
	private static final String DEFAULT_PASSWORD = Constants.DEFAULT_PASSWORD;
	
	@Autowired
	public DataLoader(UserDao userRepo, NotificationDao notiRepo, VendorDao vendorRepo, TypeOfStorageDao storageRepo,
			PasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.notiRepo = notiRepo;
		this.vendorRepo = vendorRepo;
		this.storageRepo = storageRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		/*
		 * Adds users; By default in production only Kyle and Ryan will be added
		 */
		if(!userRepo.existsByUsername("kgagnon")) {
			userRepo.save(new UserModelEntity("Kyle", "Gagnon", "kgagnon", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), Roles.MANAGER));
		}
		
		if(!userRepo.existsByUsername("rhorn")) {
			userRepo.save(new UserModelEntity("Ryan", "Horn", "rhorn", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), Roles.MANAGER));
		}
		
		if(!userRepo.existsByUsername("lplumb")) {
			userRepo.save(new UserModelEntity("Lauren", "Plumb", "lplumb", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), Roles.TEAM_MEMBER));
		}
		
		/*
		 * Populate the database with information
		 */
		// Vendor
		vendorRepo.save(new VendorEntity("Sysco", "Sean", "(508) 212-6660", "Orders on Thursday"));
		vendorRepo.save(new VendorEntity("Pepsi", "Pat", "(508) 212-6660", "Orders on Friday"));
		
		// Type of Storage
		storageRepo.save(new TypeOfStorageEntity("Rack", "Used to store Claise breads"));
		storageRepo.save(new TypeOfStorageEntity("Case", "These will be our default storage item"));
		
		// Notification
		notiRepo.save(new NotificationModelEntity(NotificationMessages.CHECK_STAND_STOCK, NotificationTypes.INFO, false, 
				userRepo.findByUsername("lplumb").get()));
		notiRepo.save(new NotificationModelEntity(NotificationMessages.CHECK_STAND_STOCK, NotificationTypes.INFO, false, 
				userRepo.findByUsername("kgagnon").get()));
		notiRepo.save(new NotificationModelEntity(NotificationMessages.ZEBRA_OUT_OF_STOCK_MESSAGE, NotificationTypes.ERROR, false, 
				userRepo.findByUsername("rhorn").get()));
		
	}

	
	
}
