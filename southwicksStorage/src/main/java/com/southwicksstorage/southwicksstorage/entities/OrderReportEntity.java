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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/**
 * @author kyle
 *
 */
@Entity
@Table(name = "order_report")
public class OrderReportEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Range(min = 1, message = "Amount to order can not be less than 1")
	@Column(name = "amount_to_order")
	private int amountToOrder;
	
	@NotNull(message = "Storage item can not be empty")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "storage_item_id", referencedColumnName="id")
	private StorageItemEntity storageItem;
	
	public OrderReportEntity() {
		
	}
	
	public OrderReportEntity(int amountToOrder, StorageItemEntity storageItem) {
		this.amountToOrder = amountToOrder;
		this.storageItem = storageItem;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmountToOrder() {
		return amountToOrder;
	}

	public void setAmountToOrder(int amountToOrder) {
		this.amountToOrder = amountToOrder;
	}

	public StorageItemEntity getStorageItem() {
		return storageItem;
	}

	public void setStorageItem(StorageItemEntity storageItem) {
		this.storageItem = storageItem;
	}
	
	
	
}
