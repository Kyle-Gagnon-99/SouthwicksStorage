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

/**
 * @author kyle
 *
 */
@Entity
@Table(name = "stand_item")
public class StandItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message = "Amount in stand can not be empty")
	@Column(name = "amount")
	private int amount;
	
	@NotEmpty(message = "Storage item can not be empty")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_item_id", referencedColumnName="id")
	private StorageItemEntity storageItem;
	
	@NotEmpty(message = "Stand name can not be empty")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stand_id", referencedColumnName="id")
	private StandEntity standId;
	
	public StandItemEntity() {
		
	}
	
	public StandItemEntity(int id, @NotEmpty(message = "Amount in stand can not be empty") int amount,
			@NotEmpty(message = "Storage item can not be empty") StorageItemEntity storageItem,
			@NotEmpty(message = "Stand name can not be empty") StandEntity standId) {
		this.id = id;
		this.amount = amount;
		this.storageItem = storageItem;
		this.standId = standId;
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

	public StorageItemEntity getStorageItem() {
		return storageItem;
	}

	public void setStorageItem(StorageItemEntity storageItem) {
		this.storageItem = storageItem;
	}

	public StandEntity getStandId() {
		return standId;
	}

	public void setStandId(StandEntity standId) {
		this.standId = standId;
	}

}
