/**
 * 
 */
package com.southwicksstorage.southwicksstorage.configurations;

import java.util.List;

import com.southwicksstorage.southwicksstorage.entities.NotificationMessageEntity;
import com.southwicksstorage.southwicksstorage.entities.OrderReportEntity;
import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.models.system.StandEmptyMessage;
import com.southwicksstorage.southwicksstorage.models.system.StandLowMessage;
import com.southwicksstorage.southwicksstorage.repositories.NotificationMessageDao;
import com.southwicksstorage.southwicksstorage.repositories.OrderReportDao;

/**
 * Common methods to use across the system
 * @author kyle
 *
 */
public class CommonMethods {

	/**
	 * If we have less than the amount of expected items then we need to add it to the report
	 * @param storageItem The storage item to check to add to the report
	 * @param repo The Order Report Repository
	 */
	public static void addToOrderReport(StorageItemEntity storageItem, OrderReportDao repo) {
		
		int amountToOrder = storageItem.getAmountExpected() - storageItem.getAmount();
		
		if(amountToOrder >= 1) {
			if(!repo.existsByStorageItem(storageItem)) {
				OrderReportEntity addToOrderReport = new OrderReportEntity(amountToOrder, storageItem);
				repo.saveAndFlush(addToOrderReport);
			} else {
				OrderReportEntity editExistingItem = repo.findByStorageItem(storageItem).get();
				editExistingItem.setAmountToOrder(amountToOrder);
				repo.saveAndFlush(editExistingItem);
			}
		}
		
	}
	
	/**
	 * Update the order report looking at all storage items
	 * @param itemList The list of items from storage to update
	 * @param repo The Order Report Repository
	 */
	public static void updateOrderReport(List<StorageItemEntity> itemList, OrderReportDao repo) {
		
		itemList.stream().forEach((storageItem) -> {
			
			int amountToOrder = storageItem.getAmountExpected() - storageItem.getAmount();
			
			if(amountToOrder >= 1) {
				if(!repo.existsByStorageItem(storageItem)) {
					OrderReportEntity addToOrderReport = new OrderReportEntity(amountToOrder, storageItem);
					repo.saveAndFlush(addToOrderReport);
				} else {
					OrderReportEntity editExistingItem = repo.findByStorageItem(storageItem).get();
					editExistingItem.setAmountToOrder(amountToOrder);
					repo.saveAndFlush(editExistingItem);
				}
			}
			
		});
		
	}
	
	/**
	 * Used to add a list of stand's empty and low notification messages to the database
	 * @param standList
	 * @param repo
	 */
	public static void addStandListLowEmptyMessages(List<StandEntity> standList, NotificationMessageDao repo) {
		
		if(standList != null) {
			standList.stream().forEach((stand) -> {
				StandEmptyMessage standEmpty = new StandEmptyMessage(stand);
				StandLowMessage standLow = new StandLowMessage(stand);
				
				repo.save(new NotificationMessageEntity(standEmpty.toString()));
				repo.save(new NotificationMessageEntity(standLow.toString()));
			});
		}
		
	}
	
	/**
	 * Used to add one stand's empty and low notification messages to the database
	 * @param stand
	 * @param repo
	 */
	public static void addStandLowEmptyMessage(StandEntity stand, NotificationMessageDao repo) {
		
		if(stand != null) {
			StandEmptyMessage standEmpty = new StandEmptyMessage(stand);
			StandLowMessage standLow = new StandLowMessage(stand);
			
			repo.save(new NotificationMessageEntity(standEmpty.toString()));
			repo.save(new NotificationMessageEntity(standLow.toString()));
		}
		
	}
	
}
