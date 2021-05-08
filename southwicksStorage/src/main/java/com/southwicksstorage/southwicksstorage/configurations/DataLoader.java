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
import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.repositories.NotificationDao;
import com.southwicksstorage.southwicksstorage.repositories.StandDao;
import com.southwicksstorage.southwicksstorage.repositories.StandItemDao;
import com.southwicksstorage.southwicksstorage.repositories.StorageItemDao;
import com.southwicksstorage.southwicksstorage.repositories.TypeOfStorageDao;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;
import com.southwicksstorage.southwicksstorage.repositories.VendorDao;

@Component
public class DataLoader implements ApplicationRunner {

	private UserDao userRepo;
	private NotificationDao notiRepo;
	private VendorDao vendorRepo;
	private TypeOfStorageDao storageRepo;
	private StorageItemDao itemRepo;
	private StandDao standRepo;
	private StandItemDao standItemRepo;
	private PasswordEncoder bCryptPasswordEncoder;
	
	private static final String DEFAULT_PASSWORD = Constants.DEFAULT_PASSWORD;
	
	@Autowired
	public DataLoader(UserDao userRepo, NotificationDao notiRepo, VendorDao vendorRepo, TypeOfStorageDao storageRepo,
			StorageItemDao itemRepo, StandDao standRepo, StandItemDao standItemRepo, PasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.notiRepo = notiRepo;
		this.vendorRepo = vendorRepo;
		this.storageRepo = storageRepo;
		this.itemRepo = itemRepo;
		this.standRepo = standRepo;
		this.standItemRepo = standItemRepo;
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
		vendorRepo.save(new VendorEntity("New England Ice Cream", "John", "(508) 212-6660", "Orders on Thursday"));
		
		// Type of Storage
		storageRepo.save(new TypeOfStorageEntity("Rack", "Used to store Claise breads"));
		storageRepo.save(new TypeOfStorageEntity("Case", "These will be our default storage item"));
		storageRepo.save(new TypeOfStorageEntity("Tub", "Used to store ice cream"));
		
		// Notification
		notiRepo.save(new NotificationModelEntity(NotificationMessages.CHECK_STAND_STOCK, NotificationTypes.INFO, false, 
				userRepo.findByUsername("lplumb").get()));
		notiRepo.save(new NotificationModelEntity(NotificationMessages.CHECK_STAND_STOCK, NotificationTypes.INFO, false, 
				userRepo.findByUsername("kgagnon").get()));
		notiRepo.save(new NotificationModelEntity(NotificationMessages.ZEBRA_OUT_OF_STOCK_MESSAGE, NotificationTypes.ERROR, false, 
				userRepo.findByUsername("rhorn").get()));
		
		// Stand
		standRepo.save(new StandEntity("Zebra", null));
		standRepo.save(new StandEntity("Ice Cream", null));
		standRepo.save(new StandEntity("Deli", null));
		
		VendorEntity pepsiVendor = vendorRepo.findByVendorName("Pepsi").get();
		VendorEntity syscoVendor = vendorRepo.findByVendorName("Sysco").get();
		VendorEntity newEnglandIC = vendorRepo.findByVendorName("New England Ice Cream").get();
		
		TypeOfStorageEntity rackStore = storageRepo.findByName("Rack").get();
		TypeOfStorageEntity caseStore = storageRepo.findByName("Case").get();
		TypeOfStorageEntity tubStore = storageRepo.findByName("Tub").get();
		
		StandEntity zebraStand = standRepo.findByName("Zebra").get();
		StandEntity iceCreamStand = standRepo.findByName("Ice Cream").get();
		StandEntity deliStand = standRepo.findByName("Deli").get();
		
		// Storage Item
		itemRepo.save(new StorageItemEntity("Fries", 5, 10, StorageType.FROZEN_STORAGE, null, syscoVendor, caseStore));
		itemRepo.save(new StorageItemEntity("Pepsi", 8, 9, StorageType.DRY_STORAGE, "July 4th so order ahead", pepsiVendor, null));
		itemRepo.save(new StorageItemEntity("Spiderman Ice Cream", 3, 10, StorageType.FROZEN_STORAGE, null, newEnglandIC, caseStore));
		itemRepo.save(new StorageItemEntity("Lettuce", 3, 7, StorageType.REFRIGERATED_STORAGE, null, syscoVendor, caseStore));
		itemRepo.save(new StorageItemEntity("Mint Ice Cream", 1, 4, StorageType.FROZEN_STORAGE, "Almost out", newEnglandIC, tubStore));
		itemRepo.save(new StorageItemEntity("16\" Pizza Cardboard", 4, 4, StorageType.DRY_STORAGE, null, syscoVendor, caseStore));
		
		StorageItemEntity friesItem = itemRepo.findByName("Fries").get();
		StorageItemEntity pepsiItem = itemRepo.findByName("Pepsi").get();
		
		//Stand Item
		standItemRepo.save(new StandItemEntity(3, 5, "Out until Friday May 7th", friesItem, zebraStand));
		standItemRepo.save(new StandItemEntity(1, 4, null, pepsiItem, zebraStand));
		standItemRepo.save(new StandItemEntity(2, 4, null, pepsiItem, iceCreamStand));
		standItemRepo.save(new StandItemEntity(1, 1, null, itemRepo.findByName("Spiderman Ice Cream").get(), zebraStand));
		standItemRepo.save(new StandItemEntity(1, 1, null, itemRepo.findByName("Spiderman Ice Cream").get(), iceCreamStand));
		standItemRepo.save(new StandItemEntity(0, 1, null, itemRepo.findByName("Spiderman Ice Cream").get(), deliStand));
		standItemRepo.save(new StandItemEntity(1, 2, null, itemRepo.findByName("Lettuce").get(), deliStand));
		standItemRepo.save(new StandItemEntity(1, 1, "Don't turn it to 0 until lettuce is half way out", itemRepo.findByName("Lettuce").get(), zebraStand));
		standItemRepo.save(new StandItemEntity(1, 1, null, itemRepo.findByName("16\" Pizza Cardboard").get(), zebraStand));
		standItemRepo.save(new StandItemEntity(1, 2, null, itemRepo.findByName("Mint Ice Cream").get(), iceCreamStand));
	}

	
	
}
