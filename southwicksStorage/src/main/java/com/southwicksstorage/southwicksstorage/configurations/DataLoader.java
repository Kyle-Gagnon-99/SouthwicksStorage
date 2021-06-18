package com.southwicksstorage.southwicksstorage.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.NotificationMessages;
import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.constants.SystemSettingsName;
import com.southwicksstorage.southwicksstorage.entities.NotificationMessageEntity;
import com.southwicksstorage.southwicksstorage.entities.SystemSettingsEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.services.NotificationMessageService;
import com.southwicksstorage.southwicksstorage.services.NotificationService;
import com.southwicksstorage.southwicksstorage.services.StandItemService;
import com.southwicksstorage.southwicksstorage.services.StandService;
import com.southwicksstorage.southwicksstorage.services.StorageItemService;
import com.southwicksstorage.southwicksstorage.services.SystemSettingsService;
import com.southwicksstorage.southwicksstorage.services.TypeOfStorageService;
import com.southwicksstorage.southwicksstorage.services.UserService;
import com.southwicksstorage.southwicksstorage.services.VendorService;

@Component
@Order(value = 1)
@Profile("production")
public class DataLoader implements ApplicationRunner {

	private UserService userService;
	@SuppressWarnings("unused")
	private NotificationService notiService;
	private NotificationMessageService notiMessageService;
	@SuppressWarnings("unused")
	private VendorService vendorService;
	@SuppressWarnings("unused")
	private TypeOfStorageService storageService;
	@SuppressWarnings("unused")
	private StorageItemService itemService;
	private StandService standService;
	@SuppressWarnings("unused")
	private StandItemService standItemService;
	private SystemSettingsService systemSettingsService;
	private PasswordEncoder bCryptPasswordEncoder;
	
	private static final String DEFAULT_PASSWORD = Constants.DEFAULT_PASSWORD;
	
	@Autowired
	public DataLoader(UserService userService, NotificationService notiService, VendorService vendorService, TypeOfStorageService storageService,
			StorageItemService itemService, StandService standService, StandItemService standItemService, NotificationMessageService notiMessageService, 
			SystemSettingsService systemSettingsService, PasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.notiService = notiService;
		this.notiMessageService = notiMessageService;
		this.vendorService = vendorService;
		this.storageService = storageService;
		this.itemService = itemService;
		this.standService = standService;
		this.standItemService = standItemService;
		this.systemSettingsService = systemSettingsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		/*
		 * Adds users; By default in production only Kyle and Ryan will be added
		 */
		if(!userService.existsByUsername("kgagnon")) {
			userService.save(new UserModelEntity("Kyle", "Gagnon", "kgagnon", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), Roles.MANAGER, "(508) 212-6660"));
		}
		
		if(!userService.existsByUsername("rhorn")) {
			userService.save(new UserModelEntity("Ryan", "Horn", "rhorn", bCryptPasswordEncoder.encode(DEFAULT_PASSWORD), Roles.MANAGER, null));
		}
		
		/*
		 * Populate the database with information
		 *
		// Vendor
		vendorService.save(new VendorEntity("Sysco", "Sean", "(508) 212-6660", "Orders on Thursday"));
		vendorService.save(new VendorEntity("Pepsi", "Pat", "(508) 212-6660", "Orders on Friday"));
		vendorService.save(new VendorEntity("New England Ice Cream", "John", "(508) 212-6660", "Orders on Thursday"));
		
		// Type of Storage
		storageService.save(new TypeOfStorageEntity("Rack", "Used to store Claise breads"));
		storageService.save(new TypeOfStorageEntity("Case", "These will be our default storage item"));
		storageService.save(new TypeOfStorageEntity("Tub", "Used to store ice cream"));
		
		// Stand
		standService.save(new StandEntity("Zebra", null));
		standService.save(new StandEntity("Ice Cream", null));
		standService.save(new StandEntity("Deli", null));
		
		VendorEntity pepsiVendor = vendorService.findByVendorName("Pepsi");
		VendorEntity syscoVendor = vendorService.findByVendorName("Sysco");
		VendorEntity newEnglandIC = vendorService.findByVendorName("New England Ice Cream");
		
		TypeOfStorageEntity rackStore = storageService.findByName("Rack");
		TypeOfStorageEntity caseStore = storageService.findByName("Case");
		TypeOfStorageEntity tubStore = storageService.findByName("Tub");
		
		StandEntity zebraStand = standService.findByName("Zebra");
		StandEntity iceCreamStand = standService.findByName("Ice Cream");
		StandEntity deliStand = standService.findByName("Deli");
		
		// Storage Item
		itemService.save(new StorageItemEntity("Fries", 5, 10, StorageType.FROZEN_STORAGE, null, syscoVendor, caseStore));
		itemService.save(new StorageItemEntity("Pepsi", 8, 9, StorageType.DRY_STORAGE, "July 4th so order ahead", pepsiVendor, null));
		itemService.save(new StorageItemEntity("Spiderman Ice Cream", 3, 10, StorageType.FROZEN_STORAGE, null, newEnglandIC, caseStore));
		itemService.save(new StorageItemEntity("Lettuce", 3, 7, StorageType.REFRIGERATED_STORAGE, null, syscoVendor, caseStore));
		itemService.save(new StorageItemEntity("Mint Ice Cream", 1, 4, StorageType.FROZEN_STORAGE, "Almost out", newEnglandIC, tubStore));
		itemService.save(new StorageItemEntity("16\" Pizza Cardboard", 4, 4, StorageType.DRY_STORAGE, null, syscoVendor, caseStore));
		
		StorageItemEntity friesItem = itemService.findByName("Fries");
		StorageItemEntity pepsiItem = itemService.findByName("Pepsi");
		
		//Stand Item
		standItemService.save(new StandItemEntity(3, 5, "Out until Friday May 7th", friesItem, zebraStand));
		standItemService.save(new StandItemEntity(1, 4, null, pepsiItem, zebraStand));
		standItemService.save(new StandItemEntity(2, 4, null, pepsiItem, iceCreamStand));
		standItemService.save(new StandItemEntity(1, 1, null, itemService.findByName("Spiderman Ice Cream"), zebraStand));
		standItemService.save(new StandItemEntity(1, 1, null, itemService.findByName("Spiderman Ice Cream"), iceCreamStand));
		standItemService.save(new StandItemEntity(0, 1, null, itemService.findByName("Spiderman Ice Cream"), deliStand));
		standItemService.save(new StandItemEntity(1, 2, null, itemService.findByName("Lettuce"), deliStand));
		standItemService.save(new StandItemEntity(1, 1, "Don't turn it to 0 until lettuce is half way out", itemService.findByName("Lettuce"), zebraStand));
		standItemService.save(new StandItemEntity(1, 1, null, itemService.findByName("16\" Pizza Cardboard"), zebraStand));
		standItemService.save(new StandItemEntity(1, 2, null, itemService.findByName("Mint Ice Cream"), iceCreamStand)); */
		
		// Add all constant notification messages
		if(notiMessageService.findByMessage(NotificationMessages.DEFAULT_PASSWORD_MESSAGE.getMessage()) == null) {
			notiMessageService.save(new NotificationMessageEntity(NotificationMessages.DEFAULT_PASSWORD_MESSAGE.getMessage()));
		}
		
		// Add any messages that have to do with the stand
		CommonMethods.addStandListLowEmptyMessages(standService.findAll(), notiMessageService);
		
		// Add any other messages
		
		/*
		 * Initialize the system settings
		 */
		if(systemSettingsService.findBySettingsName(SystemSettingsName.LOW_THRESHOLD) == null) {
			systemSettingsService.save(new SystemSettingsEntity(SystemSettingsName.LOW_THRESHOLD, Constants.LOW_THRESHOLD_DEFAULT));
		}
		if(systemSettingsService.findBySettingsName(SystemSettingsName.OUT_THRESHOLD) == null) {
			systemSettingsService.save(new SystemSettingsEntity(SystemSettingsName.OUT_THRESHOLD, Constants.OUT_THRESHOLD_DEFAULT));
		}
	}

	
	
}
