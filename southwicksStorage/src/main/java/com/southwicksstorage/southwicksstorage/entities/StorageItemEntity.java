/**
 * 
 */
package com.southwicksstorage.southwicksstorage.entities;

import java.io.Serializable;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Range;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.StorageType;

/**
 * @author kyle
 *
 */
@Entity
@Table(name = "storage_item")
@Audited
public class StorageItemEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5373908574936516693L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message = "Storage item name can not be empty")
	@Size(max = 100, message = "Storage item name can not exceed 100 characters")
	@Column(name = "name")
	private String name;
	
	@Range(min = 0, message = "Amount in storage can not be less than 0")
	@Column(name = "amount")
	private int amount;
	
	@Range(min = 0, message = "Amount expected in storage per week can not be less than 0")
	@Column(name = "amount_expected")
	private int amountExpected;
	
	@NotNull(message = "Storage in type can not be empty")
	@Column(name = "stored_in")
	private StorageType storedType;
	
	@Size(max = 500, message = Constants.ADDITIONAL_INFO_EXCEED)
	@Column(name = "additional_info")
	private String additionalInfo;
	
	@NotNull(message = "You need to have a vendor associated to this item")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vendor_id", referencedColumnName="id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private VendorEntity vendor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_of_storage_id", referencedColumnName="id")
	private TypeOfStorageEntity typeOfStorage;
	
	public StorageItemEntity() {
		
	}

	public StorageItemEntity(
			String name,
			int amount,
			int amountExpected,
			StorageType storedType,
			String additionalInfo,
			VendorEntity vendor,
			TypeOfStorageEntity typeOfStorage) {
		this.name = name;
		this.amount = amount;
		this.amountExpected = amountExpected;
		this.storedType = storedType;
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
	
	public int getAmountExpected() {
		return amountExpected;
	}
	
	public void setAmountExpected(int amountExpected) {
		this.amountExpected = amountExpected;
	}

	public StorageType getStoredType() {
		return storedType;
	}

	public void setStoredType(StorageType storedType) {
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

	public TypeOfStorageEntity getTypeOfStorage() {
		return typeOfStorage;
	}

	public void setTypeOfStorage(TypeOfStorageEntity typeOfStorage) {
		this.typeOfStorage = typeOfStorage;
	}
	
	@Override
	public String toString() {
		
		String returnString = "";
		
		if(typeOfStorage != null) {
			returnString = String.format("Id: %d\nName: %s\nAmount: %d\nAmount Expected: %d\nStored In: %s\nAdditional Info: %s\nVendor Name: %s\nType of Storage: %s\n", 
					this.id, this.name, this.amount, this.amountExpected, this.storedType.getStorageTypeName(), this.additionalInfo, this.vendor.getVendorName(),
					this.typeOfStorage.getName());
		} else {
			returnString = String.format("Id: %d\nName: %s\nAmount: %d\nAmount Expected: %d\nStored In: %s\nAdditional Info: %s\nVendor Name: %s\nType of Storage: %s\n", 
					this.id, this.name, this.amount, this.amountExpected, this.storedType.getStorageTypeName(), this.additionalInfo, this.vendor.getVendorName(),
					null);
		}
		
		return returnString;
	}
	
}
