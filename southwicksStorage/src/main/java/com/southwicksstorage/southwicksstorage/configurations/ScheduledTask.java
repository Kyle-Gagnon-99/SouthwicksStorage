
package com.southwicksstorage.southwicksstorage.configurations;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.entities.AuditedRevisionEntity;
import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.models.system.LogTask;
import com.southwicksstorage.southwicksstorage.services.OrderReportService;
import com.southwicksstorage.southwicksstorage.services.StorageItemService;

/**
 * @author kyle
 *
 */
@Component
public class ScheduledTask {
	
	@Autowired
	private EntityManagerFactory emf;
	
	@Autowired
	private OrderReportService orderReportService;
	
	@Autowired
	private StorageItemService storageItemService;
	
	private static Logger log = LoggerFactory.getLogger(ScheduledTask.class);

	
	/**
	 * At the top of every hour get all audit log entries and then format the time each revision was created at
	 * If the revision date is older than {@value Constants#DELETE_AUDIT_ENTRIES} days
	 * then delete the revisions in the table
	 * 
	 * <p> This task is here to maintain the size of the database. This is to keep the audit log from overfilling the database
	 * In addition to this task we will possibly add a task to vacuum the database once a week to condense the data
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron = Constants.EVERY_HOUR)
	public void deleteOldLogs() {
		
		LogTask logTask = new LogTask(ScheduledTask.class, Constants.DELETE_OLD_LOGS_TASK_NAME);
		logTask.startTaskLog();
		
		// Initialize
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		AuditReader auditReader = AuditReaderFactory.get(em);
		String deleteFromStorageAuditString = "DELETE FROM storage_item_aud WHERE id = :entityId ; DELETE FROM audited_revision_entity WHERE id = :revId ;";
		String deleteFromStandAuditString = "DELETE FROM stand_item_aud WHERE id = :entityId ; DELETE FROM audited_revision_entity WHERE id = :revId ;";
		String deleteOtherAudTableString = "DELETE FROM vendor_aud; DELETE FROM type_of_storage_aud; DELETE FROM stand_aud";
		Query deleteStorageAuditEntry = em.createNativeQuery(deleteFromStorageAuditString);
		Query deleteStandAuditEntry = em.createNativeQuery(deleteFromStandAuditString);
		Query deleteOtherAuditTable = em.createNativeQuery(deleteOtherAudTableString);
		deleteOtherAuditTable.executeUpdate();
		
		List<Object[]> auditStorageItems = (List<Object[]>) auditReader.createQuery().forRevisionsOfEntity(StorageItemEntity.class, false, true).getResultList();
		List<Object[]> auditStandItems = (List<Object[]>) auditReader.createQuery().forRevisionsOfEntity(StandItemEntity.class, false, true).getResultList();
		
		auditStorageItems.stream().forEach((auditItem) -> {
			StorageItemEntity storageItem = (StorageItemEntity) auditItem[0];
			AuditedRevisionEntity currentRevInfo = (AuditedRevisionEntity) auditItem[1];
			LocalDateTime currentRevTime = LocalDateTime.parse(currentRevInfo.getDateModified(), Constants.formatter);
			LocalDateTime dateAhead = currentRevTime.plus(Constants.DELETE_AUDIT_ENTRIES, Constants.DELETE_AUDIT_ENTRIES_TIME_UNIT);
			String currentTimeUnformatted = LocalDateTime.now().format(Constants.formatter);
			LocalDateTime currentTimeFormatted = LocalDateTime.parse(currentTimeUnformatted, Constants.formatter);
			
			if(currentTimeFormatted.isAfter(dateAhead)) {
				log.info("Deleting revision {}. The log was created at {} and it was scheduled to expire {}. It is currently {}", 
						currentRevInfo.getId(), currentRevTime, dateAhead, currentTimeFormatted);
				deleteStorageAuditEntry.setParameter("entityId", storageItem.getId());
				deleteStorageAuditEntry.setParameter("revId", currentRevInfo.getId());
				deleteStorageAuditEntry.executeUpdate();
				
			}
			
		});
		
		auditStandItems.stream().forEach((auditItem) -> {
			StandItemEntity standItem = (StandItemEntity) auditItem[0];
			AuditedRevisionEntity currentRevInfo = (AuditedRevisionEntity) auditItem[1];
			LocalDateTime currentRevTime = LocalDateTime.parse(currentRevInfo.getDateModified(), Constants.formatter);
			LocalDateTime dateAhead = currentRevTime.plus(Constants.DELETE_AUDIT_ENTRIES, Constants.DELETE_AUDIT_ENTRIES_TIME_UNIT);
			String currentTimeUnformatted = LocalDateTime.now().format(Constants.formatter);
			LocalDateTime currentTimeFormatted = LocalDateTime.parse(currentTimeUnformatted, Constants.formatter);
			
			if(currentTimeFormatted.isAfter(dateAhead)) {
				log.info("Deleting revision {}. The log was created at {} and it was scheduled to expire {}. It is currently {}", 
						currentRevInfo.getId(), currentRevTime, dateAhead, currentTimeFormatted);
				deleteStandAuditEntry.setParameter("entityId", standItem.getId());
				deleteStandAuditEntry.setParameter("revId", currentRevInfo.getId());
				deleteStandAuditEntry.executeUpdate();
				
			}
		});
		
		em.getTransaction().commit();
		em.close();
		
		logTask.endTaskLog();
	
	}
	
	/**
	 * Check the storage item list and update the order report every 15 minutes
	 */
	@Scheduled(cron = Constants.EVERY_FIFTEEN_MIN)
	public void updateOrderReport() {
		LogTask logTask = new LogTask(ScheduledTask.class, Constants.UPDATE_ORDER_REPORT);
		logTask.startTaskLog();
		CommonMethods.updateOrderReport(storageItemService.findAll(), orderReportService);
		logTask.endTaskLog();
	}

}
