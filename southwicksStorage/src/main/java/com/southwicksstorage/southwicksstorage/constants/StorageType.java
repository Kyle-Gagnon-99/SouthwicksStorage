/**
 * 
 */
package com.southwicksstorage.southwicksstorage.constants;

/**
 * @author kyle
 *
 */
public enum StorageType {
	
	DRY_STORAGE("Dry Goods"),
	REFRIGERATED_STORAGE("Refrigerated Goods"),
	FROZEN_STORAGE("Frozen Storage");
	
	private String storageTypeName;
	
	private StorageType(String storageTypeName) {
		this.storageTypeName = storageTypeName;
	}
	
	public String getStorageTypeName() {
		return storageTypeName;
	}

}
