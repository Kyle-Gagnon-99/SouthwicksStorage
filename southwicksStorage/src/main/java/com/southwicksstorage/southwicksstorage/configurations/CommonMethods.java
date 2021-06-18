/**
 * 
 */
package com.southwicksstorage.southwicksstorage.configurations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;
import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.entities.NotificationMessageEntity;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.OrderReportEntity;
import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.system.StandEmptyMessage;
import com.southwicksstorage.southwicksstorage.models.system.StandLowMessage;
import com.southwicksstorage.southwicksstorage.services.NotificationMessageService;
import com.southwicksstorage.southwicksstorage.services.NotificationService;
import com.southwicksstorage.southwicksstorage.services.OrderReportService;
import com.southwicksstorage.southwicksstorage.services.StandItemService;
import com.southwicksstorage.southwicksstorage.services.StandService;
import com.southwicksstorage.southwicksstorage.services.UserService;

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
	public static void addToOrderReport(StorageItemEntity storageItem, OrderReportService orderReportService) {
		
		int amountToOrder = storageItem.getAmountExpected() - storageItem.getAmount();
		
		if(amountToOrder >= 1) {
			if(!orderReportService.existsByStorageItem(storageItem)) {
				OrderReportEntity addToOrderReport = new OrderReportEntity(amountToOrder, storageItem);
				orderReportService.save(addToOrderReport);
			} else {
				OrderReportEntity editExistingItem = orderReportService.findByStorageItem(storageItem);
				editExistingItem.setAmountToOrder(amountToOrder);
				orderReportService.save(editExistingItem);
			}
		}
		
	}
	
	/**
	 * Update the order report looking at all storage items
	 * @param itemList The list of items from storage to update
	 * @param service The Order Report Service
	 */
	public static void updateOrderReport(List<StorageItemEntity> itemList, OrderReportService service) {
		
		itemList.stream().forEach((storageItem) -> {
			
			int amountToOrder = storageItem.getAmountExpected() - storageItem.getAmount();
			
			if(amountToOrder >= 1) {
				if(!service.existsByStorageItem(storageItem)) {
					OrderReportEntity addToOrderReport = new OrderReportEntity(amountToOrder, storageItem);
					service.save(addToOrderReport);
				} else {
					OrderReportEntity editExistingItem = service.findByStorageItem(storageItem);
					editExistingItem.setAmountToOrder(amountToOrder);
					service.save(editExistingItem);
				}
			}
			
		});
		
	}
	
	/**
	 * Used to add a list of stand's empty and low notification messages to the database
	 * @param standList
	 * @param repo
	 */
	public static void addStandListLowEmptyMessages(List<StandEntity> standList, NotificationMessageService notiMessageService) {
		
		if(standList != null) {
			standList.stream().forEach((stand) -> {
				StandEmptyMessage standEmpty = new StandEmptyMessage(stand);
				StandLowMessage standLow = new StandLowMessage(stand);
				
				if(notiMessageService.findByMessage(standEmpty.toString()) == null) {
					notiMessageService.save(new NotificationMessageEntity(standEmpty.toString()));
				}
				if(notiMessageService.findByMessage(standLow.toString()) == null) {
					notiMessageService.save(new NotificationMessageEntity(standLow.toString()));
				}
			});
		}
		
	}
	
	/**
	 * Used to add one stand's empty and low notification messages to the database
	 * @param stand
	 * @param repo
	 */
	public static void addStandLowEmptyMessage(StandEntity stand, NotificationMessageService notiMessageService) {
		
		if(stand != null) {
			StandEmptyMessage standEmpty = new StandEmptyMessage(stand);
			StandLowMessage standLow = new StandLowMessage(stand);
			
			if(notiMessageService.findByMessage(standEmpty.toString()) == null) {
				notiMessageService.save(new NotificationMessageEntity(standEmpty.toString()));
			}
			if(notiMessageService.findByMessage(standLow.toString()) == null) {
				notiMessageService.save(new NotificationMessageEntity(standLow.toString()));
			}
		}
		
	}
	
	/**
	 * Add any low / out notifications to managers when a stand item is changed
	 * @param managerList
	 * @param standItems
	 * @param notiRepo
	 * @param notiMessageRepo
	 */
	public static void addAnyNotifications(List<UserModelEntity> managerList, List<StandItemEntity> standItems, NotificationService notiService, 
			NotificationMessageService notiMessageService) {
		float lowThreshold = SystemVariables.lowStandThreshold;
		float emptyThreshold = SystemVariables.emptyStandThreshold;
		
		standItems.stream().forEach((item) -> {
			float itemPercent = ((float) item.getAmount() / (float) item.getAmountExpected());
			
			if(itemPercent <= lowThreshold && itemPercent > emptyThreshold) {
				NotificationMessageEntity lowStandMessage = notiMessageService.findByMessage(new StandLowMessage(item.getStand()).getMessage());
				managerList.stream().forEach((user) -> {
					NotificationModelEntity lowStandNoti = notiService.findByMessageAndUser(lowStandMessage, user);
					
					if(lowStandNoti == null) {
						NotificationModelEntity createLowStandNoti = new NotificationModelEntity(lowStandMessage, NotificationTypes.WARNING, false, true, 
								LocalDateTime.now(), user);
						notiService.save(createLowStandNoti);
					} else {
						if(LocalDateTime.now().getDayOfMonth() != lowStandNoti.getDateCreated().getDayOfMonth()) {
							lowStandNoti.setIsVisible(true);
						}
						notiService.save(lowStandNoti);
					}
				});
			} else if(itemPercent <= emptyThreshold) {
				NotificationMessageEntity emptyStandMessage = notiMessageService.findByMessage(new StandEmptyMessage(item.getStand()).getMessage());
				managerList.stream().forEach((user) -> {
					NotificationModelEntity emptyStandNoti = notiService.findByMessageAndUser(emptyStandMessage, user);
					
					if(emptyStandNoti == null) {
						NotificationModelEntity createEmptyStandNoti = new NotificationModelEntity(emptyStandMessage, NotificationTypes.ERROR, false, true,
								LocalDateTime.now(), user);
						notiService.save(createEmptyStandNoti);
					} else {
						if(LocalDateTime.now().getDayOfMonth() != emptyStandNoti.getDateCreated().getDayOfMonth()) {
							emptyStandNoti.setIsVisible(true);
						}
						notiService.save(emptyStandNoti);
					}
				});
			}
		});
		
	}
	
	/**
	 * Remove any low / empty notifications if all items are not low for the low notification and not empty the empty
	 * All low and empty percents can be edited by the managers
	 * @param standService
	 * @param standItemService
	 * @param userService
	 * @param notiService
	 * @param notiMessageService
	 */
	public static void removeAnyNotifications(StandService standService, StandItemService standItemService, UserService userService, 
			NotificationService notiService, NotificationMessageService notiMessageService) {
		float lowThreshold = SystemVariables.lowStandThreshold;
		float emptyThreshold = SystemVariables.emptyStandThreshold;
		AtomicBoolean noItemIsLow = new AtomicBoolean(true);
		AtomicBoolean noItemIsEmpty = new AtomicBoolean(true);
		
		standService.findAll().stream().forEach((stand) -> {
			noItemIsLow.set(true);
			noItemIsEmpty.set(true);
			
			standItemService.findAllByStand(stand).stream().forEach((item) -> {
				float itemPercent = ((float)item.getAmount() / (float)item.getAmountExpected());
				if(itemPercent <= lowThreshold && itemPercent > emptyThreshold) {
					noItemIsLow.set(false);
				} else if(itemPercent <= emptyThreshold) {
					noItemIsEmpty.set(false);
				}
			});
			
			if(noItemIsLow.get()) {
				userService.findAllByRole(Roles.MANAGER).stream().forEach((user) -> {
					NotificationModelEntity getNotificationOptional = notiService.findByMessageAndUser(
							notiMessageService.findByMessage(new StandLowMessage(stand).getMessage()), user);
					if(getNotificationOptional != null) {
						notiService.delete(getNotificationOptional);
					}
				});
			}
			
			if(noItemIsEmpty.get()) {
				userService.findAllByRole(Roles.MANAGER).stream().forEach((user) -> {
					NotificationModelEntity getNotification = notiService.findByMessageAndUser(
							notiMessageService.findByMessage(new StandEmptyMessage(stand).getMessage()), user);
					if(getNotification != null) {
						notiService.delete(getNotification);
					}
				});
			}
		});
	}
	
}
