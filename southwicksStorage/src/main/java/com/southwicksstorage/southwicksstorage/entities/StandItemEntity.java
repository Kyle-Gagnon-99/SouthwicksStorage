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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Range;

import com.southwicksstorage.southwicksstorage.constants.Constants;

/**
 * @author kyle
 *
 */
@Entity
@Table(name = "stand_item")
@Audited
public class StandItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Range(min = 0, message = "Amount in the stand can not be less than 0")
	@Column(name = "amount")
	private int amount;
	
	@Range(min = 0, message = "Amount expected in the stand per week can not be less than 0")
	@Column(name = "amount_expected")
	private int amountExpected;
	
	@Size(max = 500, message = Constants.ADDITIONAL_INFO_EXCEED)
	@Column(name = "additional_info")
	private String additionalInfo;
	
	@NotNull(message = "Storage item can not be empty")
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "storage_item_id", referencedColumnName="id")
	private StorageItemEntity storageItem;
	
	@NotNull(message = "Stand name can not be empty")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stand", referencedColumnName="id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private StandEntity stand;
	
	public StandItemEntity() {
		
	}
	
	public StandItemEntity(@NotEmpty(message = "Amount in stand can not be empty") int amount,
			int amountExpected,
			String additionalInfo,
			@NotEmpty(message = "Storage item can not be empty") StorageItemEntity storageItem,
			@NotEmpty(message = "Stand name can not be empty") StandEntity stand) {
		this.amount = amount;
		this.amountExpected = amountExpected;
		this.additionalInfo = additionalInfo;
		this.storageItem = storageItem;
		this.stand = stand;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public StorageItemEntity getStorageItem() {
		return storageItem;
	}

	public void setStorageItem(StorageItemEntity storageItem) {
		this.storageItem = storageItem;
	}

	public StandEntity getStand() {
		return stand;
	}

	public void setStand(StandEntity stand) {
		this.stand = stand;
	}

}
