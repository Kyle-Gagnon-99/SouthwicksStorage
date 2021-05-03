/**
 * 
 */
package com.southwicksstorage.southwicksstorage.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.StorageType;

/**
 * @author kyle
 *
 */
@Entity
@Table(name = "storage_item")
public class StorageItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message = "Storage item name can not be empty")
	@Size(max = 100, message = "Storage item name can not exceed 100 characters")
	@Column(name = "name")
	private String name;
	
	@NotEmpty(message = "Amount of this storage item can not be empty")
	@Column(name = "amount")
	private int amount;
	
	@NotEmpty(message = "Storage in type can not be empty")
	@Column(name = "stored_in")
	private StorageType storedIn;
	
	@Size(max = 500, message = Constants.ADDITIONAL_INFO_EXCEED)
	@Column(name = "additional_info")
	private String additionalInfo;
	
	@NotEmpty(message = "You need to have a vendor associated to this item")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendor_id", referencedColumnName="id")
	private VendorEntity vendor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_of_storage_id", referencedColumnName="id")
	private TypeOfStorageEntity typeOfStorage;
	
	public StorageItemEntity() {
		
	}

	public StorageItemEntity(int id,
			@NotEmpty(message = "Storage item name can not be empty") @Size(max = 100, message = "Storage item name can not exceed 100 characters") String name,
			@NotEmpty(message = "Amount of this storage item can not be empty") int amount,
			@NotEmpty(message = "Storage in type can not be empty") StorageType storedIn,
			@Size(max = 500, message = "Additional Information can not exceed 500 characters") String additionalInfo,
			@NotEmpty(message = "You need to have a vendor associated to this item") VendorEntity vendor,
			TypeOfStorageEntity typeOfStorage) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.storedIn = storedIn;
		this.additionalInfo = additionalInfo;
		this.vendor = vendor;
		this.typeOfStorage = typeOfStorage;
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

	public StorageType getStoredIn() {
		return storedIn;
	}

	public void setStoredIn(StorageType storedIn) {
		this.storedIn = storedIn;
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

	public TypeOfStorageEntity getTypeOfStorage() {
		return typeOfStorage;
	}

	public void setTypeOfStorage(TypeOfStorageEntity typeOfStorage) {
		this.typeOfStorage = typeOfStorage;
	}
	
}