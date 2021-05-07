/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models.formModels;

/**
 * @author kyle
 *
 */
public class CreateStorageItemFormModel {
	
	private String name;
	private int amount;
	private int amountExpected;
	private String storedType;
	private String additionalInfo;
	private int vendor;
	private int typeOfStorage;
	
	public CreateStorageItemFormModel() {
		
	}

	public CreateStorageItemFormModel(String name, int amount, int amountExpected, String storedType,
			String additionalInfo, int vendor, int typeOfStorage) {
		this.name = name;
		this.amount = amount;
		this.amountExpected = amountExpected;
		this.storedType = storedType;
		this.additionalInfo = additionalInfo;
		this.vendor = vendor;
		this.typeOfStorage = typeOfStorage;
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

	public int getVendor() {
		return vendor;
	}

	public void setVendor(int vendor) {
		this.vendor = vendor;
	}

	public int getTypeOfStorage() {
		return typeOfStorage;
	}

	public void setTypeOfStorage(int typeOfStorage) {
		this.typeOfStorage = typeOfStorage;
	}
	
	

}
