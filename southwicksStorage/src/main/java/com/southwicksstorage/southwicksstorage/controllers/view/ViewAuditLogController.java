/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.entities.AuditedRevisionEntity;
import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.models.attribute.AuditLogModel;

/**
 * @author kyle
 *
 */
@Controller
public class ViewAuditLogController {
		
	@Autowired
	private EntityManagerFactory emf;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/view/auditLog", method = RequestMethod.GET)
	public ModelAndView getAuditLogView(Model model) {
		
		List<AuditLogModel> auditLog = new ArrayList<AuditLogModel>();
		
		// Initialize
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		AuditReader auditReader = AuditReaderFactory.get(em);
		AuditQuery query;
		
		List<Object[]> auditStorageIems = (List<Object[]>) auditReader.createQuery().forRevisionsOfEntity(StorageItemEntity.class, false, true).getResultList();
		List<Object[]> auditStandItems = (List<Object[]>) auditReader.createQuery().forRevisionsOfEntity(StandItemEntity.class, false, true).getResultList();
		
		auditStorageIems.stream().forEach((revisionInfo) -> {
			StorageItemEntity currentRevStorageItem = (StorageItemEntity) revisionInfo[0];
			AuditedRevisionEntity currentRevInfo = (AuditedRevisionEntity) revisionInfo[1];
			RevisionType currentRevType = (RevisionType) revisionInfo[2];
			
			boolean isInsert = false,
					isModify = false,
					isDelete = false;
			
			switch(currentRevType.toString()) {
				case "ADD":
					isInsert = true;
					break;
				case "MOD":
					isModify = true;
					break;
				case "DEL":
					isDelete = true;
					break;
				default:
					break;
			}
			
			List<Number> revNumbers = auditReader.getRevisions(StorageItemEntity.class, currentRevStorageItem.getId());
			
			if(revNumbers.size() > 1) {
				StorageItemEntity prevStorageItem = auditReader.find(StorageItemEntity.class, currentRevStorageItem.getId(), revNumbers.get(revNumbers.size() - 2));
				if(!(isModify && currentRevStorageItem.getAmount() == prevStorageItem.getAmount())) {
					auditLog.add(new AuditLogModel(currentRevInfo.getUsername(), currentRevInfo.getDateModified(), currentRevStorageItem.getName(),
							"Storage", prevStorageItem.getAmount(), currentRevStorageItem.getAmount(), isInsert, isModify, isDelete));
				}
			} else {
				auditLog.add(new AuditLogModel(currentRevInfo.getUsername(), currentRevInfo.getDateModified(), currentRevStorageItem.getName(),
						"Storage", null, currentRevStorageItem.getAmount(), isInsert, isModify, isDelete));
			}
			
		});
		
		auditStandItems.stream().forEach((revisionInfo) -> {
			StandItemEntity currentRevStandItem = (StandItemEntity) revisionInfo[0];
			AuditedRevisionEntity currentRevInfo = (AuditedRevisionEntity) revisionInfo[1];
			RevisionType currentRevType = (RevisionType) revisionInfo[2];
			
			boolean isInsert = false,
					isModify = false,
					isDelete = false;
			
			switch(currentRevType.toString()) {
				case "ADD":
					isInsert = true;
					break;
				case "MOD":
					isModify = true;
					break;
				case "DEL":
					isDelete = true;
					break;
				default:
					break;
			}
			
			List<Number> revNumbers = auditReader.getRevisions(StandItemEntity.class, currentRevStandItem.getId());
			
			if(revNumbers.size() > 1) {
				StandItemEntity prevStorageItem = auditReader.find(StandItemEntity.class, currentRevStandItem.getId(), revNumbers.get(revNumbers.size() - 2));
				auditLog.add(new AuditLogModel(currentRevInfo.getUsername(), currentRevInfo.getDateModified(), currentRevStandItem.getStorageItem().getName(),
						currentRevStandItem.getStand().getName(), prevStorageItem.getAmount(), currentRevStandItem.getAmount(), isInsert, isModify, isDelete));
			} else {
				auditLog.add(new AuditLogModel(currentRevInfo.getUsername(), currentRevInfo.getDateModified(), currentRevStandItem.getStorageItem().getName(),
						currentRevStandItem.getStand().getName(), null, currentRevStandItem.getAmount(), isInsert, isModify, isDelete));
			}
			
		});
		
		model.addAttribute("auditLog", auditLog);
		return new ModelAndView("view/viewauditlog.html");
	}

}
