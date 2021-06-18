/**
 * 
 */
package com.southwicksstorage.southwicksstorage.unit.configuration;

import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;

/**
 * @author kyle
 *
 */
public class UnitTestConstants {
	
	/*
	 * Data constants
	 */
	public static final String TEST_USERNAME_MANAGER = "tmanager";
	public static final String TEST_FNAME_MANAGER = "Test";
	public static final String TEST_LNAME_MANAGER = "Manager";
	public static final String TEST_PHONE_NUMBER = "(555) 555-5555";
	public static final String TEST_USERNAME_TM = "tteammember";
	public static final String TEST_FNAME_TM = "Test";
	public static final String TEST_LNAME_TM = "Tmember";
	public static final String TEST_PHONE_NUMBER_TM = "(444) 444-4444";
	public static final String TEST_DEFAULT_PASSWORD = "Test123!";
	
	/*
	 * Available test data objects to use
	 */
	// Users
	public static final UserModelEntity USER_MANAGER_OBJECT = new UserModelEntity(UnitTestConstants.TEST_FNAME_MANAGER, UnitTestConstants.TEST_LNAME_MANAGER, 
			UnitTestConstants.TEST_USERNAME_MANAGER, UnitTestConstants.TEST_DEFAULT_PASSWORD,
			Roles.MANAGER, null);
	public static final UserModelEntity USER_TEAM_MEMBER_OBJECT = new UserModelEntity(UnitTestConstants.TEST_FNAME_TM, UnitTestConstants.TEST_LNAME_TM,
			UnitTestConstants.TEST_USERNAME_TM, UnitTestConstants.TEST_DEFAULT_PASSWORD, 
			Roles.TEAM_MEMBER, null);
	
	// Vendors
	public static final VendorEntity VENDOR_SYSCO_OBJECT = new VendorEntity("Sysco", "Sean", "(508) 212-6660", null);
	public static final VendorEntity VENDOR_PEPSI_OBJECT = new VendorEntity("Pepsi", "Pat", "(508) 212-6660", null);
	public static final VendorEntity VENDOR_NEW_ENGLAND_ICE_CREAM_OBJECT = new VendorEntity("New England Ice Cream", "Fake", "(508) 212-6660", null);
	
	// Type of Storage
	public static final TypeOfStorageEntity TOS_CASE_OBJECT = new TypeOfStorageEntity("Case", null);
	public static final TypeOfStorageEntity TOS_TUB_OBJECT = new TypeOfStorageEntity("Tub", null);
	public static final TypeOfStorageEntity TOS_RACK_OBJECT = new TypeOfStorageEntity("Rack", null);
	
	// Stand
	public static final StandEntity STAND_ZEBRA_OBJECT = new StandEntity("Zebra", null);
	public static final StandEntity STAND_ICE_CREAM_OBJECT = new StandEntity("Ice Cream", null);
	public static final StandEntity STAND_DELI_OBJECT = new StandEntity("Deli", null);
	
	// Storage Item
	public static final StorageItemEntity STORAGE_ITEM_FRIES_OBJECT = new StorageItemEntity("Fries", 2, 4, StorageType.FROZEN_STORAGE, null, 
			VENDOR_SYSCO_OBJECT, TOS_CASE_OBJECT);
	public static final StorageItemEntity STORAGE_ITEM_SPIDER_MAN_IC_OBJECT = new StorageItemEntity("Spider Man Ice Cream", 1, 3, StorageType.FROZEN_STORAGE,
			null, VENDOR_NEW_ENGLAND_ICE_CREAM_OBJECT, TOS_CASE_OBJECT);
	public static final StorageItemEntity STORAGE_ITEM_MINT_IC_OBJECT = new StorageItemEntity("Mint Ice Cream", 3, 3, StorageType.FROZEN_STORAGE, null,
			VENDOR_NEW_ENGLAND_ICE_CREAM_OBJECT, TOS_TUB_OBJECT);
	public static final StorageItemEntity STORAGE_ITEM_PEPSI_OBJECT = new StorageItemEntity("Pepsi", 6, 7, StorageType.DRY_STORAGE, "Can't order until July 4th",
			VENDOR_PEPSI_OBJECT, TOS_RACK_OBJECT);
	public static final StorageItemEntity STORAGE_ITEM_12_INCH_PIZZA_BOARD_OBJECT = new StorageItemEntity("12\" Pizza Cardboard Box",  0, 2, 
			StorageType.DRY_STORAGE, null, VENDOR_SYSCO_OBJECT, null);
	public static final StorageItemEntity STORAGE_ITEM_LETTUCE_OBJECT = new StorageItemEntity("Lettuce", 2, 4, StorageType.REFRIGERATED_STORAGE, null,
			VENDOR_SYSCO_OBJECT, TOS_CASE_OBJECT);
	
	// Stand Item
	public static final StandItemEntity STAND_ITEM_FRIES_ZEBRA_OBJECT = new StandItemEntity(1, 2, null, STORAGE_ITEM_FRIES_OBJECT, STAND_ZEBRA_OBJECT);
	public static final StandItemEntity STAND_ITEM_SPIDER_MAN_IC_ZEBRA_OBJECT = new StandItemEntity(0, 1, null, STORAGE_ITEM_SPIDER_MAN_IC_OBJECT,
			STAND_ZEBRA_OBJECT);
	public static final StandItemEntity STAND_ITEM_12_INCE_PIZZA_ZEBRA_OBJECT = new StandItemEntity(1, 1, null, STORAGE_ITEM_12_INCH_PIZZA_BOARD_OBJECT,
			STAND_ZEBRA_OBJECT);
	public static final StandItemEntity STAND_ITEM_LETTUCE_ZEBRA_OBJECT = new StandItemEntity(1, 1, null, STORAGE_ITEM_LETTUCE_OBJECT, STAND_ZEBRA_OBJECT);
	public static final StandItemEntity STAND_ITEM_PEPSI_IC_OBJECT = new StandItemEntity(1, 1, null, STORAGE_ITEM_PEPSI_OBJECT, STAND_ICE_CREAM_OBJECT);
	public static final StandItemEntity STAND_ITEM_MINT_IC_IC_OBJECT = new StandItemEntity(1, 2, null, STORAGE_ITEM_MINT_IC_OBJECT, STAND_ICE_CREAM_OBJECT);
	public static final StandItemEntity STAND_ITEM_LETTUCE_DELI_OBJECT = new StandItemEntity(1, 1, null, STORAGE_ITEM_LETTUCE_OBJECT, STAND_DELI_OBJECT);
	
	/**
	 * Test constants
	 */
	public static final int INVALID_ID = 9999;
	public static final String INVALID_STRING = "Invalid";
}
