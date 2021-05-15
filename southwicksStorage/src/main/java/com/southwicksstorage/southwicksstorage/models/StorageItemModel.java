/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models;

import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;

/**
 * @author kyle
 *
 */
public class StorageItemModel {

	private int id;
	private String name;
	private int amount;
	private int amountExpected;
	private String storedType;
	private String additionalInfo;
	private VendorEntity vendor;
	private String typeOfStorage;
	private TypeOfStorageEntity typeOfStorageEntity;
	private StorageType storedTypeEnum;
	
	public StorageItemModel(int id, String name, int amount, int amountExpected, String storedType, String additionalInfo,
			VendorEntity vendor, String typeOfStorage, TypeOfStorageEntity typeOfStorageEntity, StorageType storedTypeEnum) {
		this.setId(id);
		this.name = name;
		this.amount = amount;
		this.amountExpected = amountExpected;
		this.storedType = storedType;
		this.additionalInfo = additionalInfo;
		this.vendor = vendor;
		this.typeOfStorage = typeOfStorage;
		this.typeOfStorageEntity = typeOfStorageEntity;
		this.storedTypeEnum = storedTypeEnum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmountExpected() {
		return amountExpected;
	}

	public void setAmountExpected(int amountExpected) {
		this.amountExpected = amountExpected;
	}

	public String getStoredType() {
		return storedType;
	}

	public void setStoredType(String storedType) {
		this.storedType = storedType;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public VendorEntity getVendor() {
		return vendor;
	}

	public void setVendor(VendorEntity vendor) {
		this.vendor = vendor;
	}

	public String getTypeOfStorage() {
		return typeOfStorage;
	}

	public void setTypeOfStorage(String typeOfStorage) {
		this.typeOfStorage = typeOfStorage;
	}

	public TypeOfStorageEntity getTypeOfStorageEntity() {
		return typeOfStorageEntity;
	}

	public void setTypeOfStorageEntity(TypeOfStorageEntity typeOfStorageEntity) {
		this.typeOfStorageEntity = typeOfStorageEntity;
	}
	
	public StorageType getStoredTypeEnum() {
		return storedTypeEnum;
	}
	
	public void setStoredTypeEnum(StorageType storedTypeEnum) {
		this.storedTypeEnum = storedTypeEnum;
	}
	
}
