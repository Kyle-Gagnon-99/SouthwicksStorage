/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.southwicksstorage.southwicksstorage.configurations.CommonMethods;
import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.models.StorageItemModel;
import com.southwicksstorage.southwicksstorage.models.formModels.CreateStorageItemFormModel;
import com.southwicksstorage.southwicksstorage.repositories.OrderReportDao;
import com.southwicksstorage.southwicksstorage.repositories.StorageItemDao;
import com.southwicksstorage.southwicksstorage.repositories.TypeOfStorageDao;
import com.southwicksstorage.southwicksstorage.repositories.VendorDao;

/**
 * @author kyle
 *
 */
@Controller
public class ViewStorageItemsController {

	@Autowired
	private StorageItemDao storageItemRepo;
	
	@Autowired
	private VendorDao vendorRepo;
	
	@Autowired
	private TypeOfStorageDao tosRepo;
	
	@Autowired
	private OrderReportDao orderReportRepo;
	
	private List<StorageItemModel> storageItemList = null;
	private Logger log = LoggerFactory.getLogger(ViewStorageItemsController.class);
	
	@RequestMapping(value = "/view/storageItem", method = RequestMethod.GET)
	public ModelAndView getViewStorageItem(Model model) {
		model.addAttribute("storageItemList", storageItemRepo.findAll());
		model.addAttribute("createStorageItemForm", new CreateStorageItemFormModel());
		List<VendorEntity> vendorList = vendorRepo.findAll();
		List<TypeOfStorageEntity> typeOfStorageList = tosRepo.findAll();
		
		if(vendorList.size() > 0) {
			model.addAttribute("vendorList", vendorList);
		}
		
		if(typeOfStorageList.size() > 0) {
			model.addAttribute("typeOfStorageList", typeOfStorageList);
		}
		
		return new ModelAndView("view/viewstorageitem.html");
	}
	
	@RequestMapping(value = "/view/storageItem/deleteItem", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteStorageItem(Integer id) {	
		try {
			storageItemRepo.delete(storageItemRepo.findById(id).get());
		} catch(Exception e) {
			log.error("Can not delete storage item {} because there are still stand items associated to this storage item (FK Constraint).", 
					storageItemRepo.findById(id).get().getName());
			return false;
		}
		
		updateStorageItemList();
		return true;
	}
	
	@RequestMapping(value = "/view/storageItem/editItem", method = RequestMethod.POST)
	@ResponseBody
	public StorageItemEntity editStorageItem(Integer id, String name, Integer amount, Integer amountExpected,
			String storedType, Integer vendor, Integer typeOfStorage, String additionalInfo) {
		
		StorageItemEntity item = storageItemRepo.findById(id).get();
		
		item.setName(name);
		item.setAmount(amount);
		item.setAmountExpected(amountExpected);
		item.setStoredType(StorageType.valueOf(storedType));
		item.setVendor(vendorRepo.findById(vendor).get());
		if(tosRepo.findById(typeOfStorage).isPresent()) {
			item.setTypeOfStorage(tosRepo.findById(typeOfStorage).get());
		} else if(typeOfStorage == -1) {
			item.setTypeOfStorage(null);
		}
		item.setAdditionalInfo(additionalInfo);
		
		storageItemRepo.saveAndFlush(item);
		
		CommonMethods.addToOrderReport(item, orderReportRepo);
		updateStorageItemList();
		
		return item;
	}
	
	@RequestMapping(value = "/view/storageItem/getAllStorageItems", method = RequestMethod.POST)
	@ResponseBody
	public List<StorageItemModel> getAllItems() {
		
		if(storageItemList == null) {
			updateStorageItemList();
		} else {
			if(storageItemList.size() == 0) {
				updateStorageItemList();
			}
		}
			
		return storageItemList;
		
	}
	
	@RequestMapping(value = "/view/storageItem/addStorageItem", method = RequestMethod.POST)
	@ResponseBody
	public boolean postViewStorageItem(String name, Integer amount, Integer amountExpected, String storedType, Integer vendorId, Integer typeOfStorageId,
			String additionalInfo) {
		TypeOfStorageEntity typeOfStorage = null;
		if(typeOfStorageId != -1) {
			typeOfStorage = tosRepo.findById(typeOfStorageId).get();
		}
		
		StorageItemEntity createStorageItem = new StorageItemEntity(name, amount, amountExpected, StorageType.valueOf(storedType),  additionalInfo,
				vendorRepo.findById(vendorId).get(), typeOfStorage);
		
		try {
			storageItemRepo.save(createStorageItem);
		} catch(Exception e) {
			log.error("Unable to create storage item {}", name);
			log.error(e.getMessage());
		}
		
		updateStorageItemList();
		return true;
	}
	
	@RequestMapping(value = "/view/storageItem/itemExists", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkIfItemExists(String name, Integer vendorId) {
		
		if(vendorRepo.findById(vendorId).isEmpty()) {
			log.error("vendorId was passed to the controller with an invalid value of {}", vendorId);
			return false;
		}
		
		return !storageItemRepo.existsByNameAndVendor(name, vendorRepo.findById(vendorId).get());
	}
	
	private void updateStorageItemList() {
		List<StorageItemEntity> storageList = storageItemRepo.findAll();
		
		if(storageItemList == null) {
			storageItemList = new ArrayList<StorageItemModel>();
		} else {
			storageItemList.clear();
		}
		
		storageList.stream().forEach((storageItem) -> {
			String typeOfStorage = null;
			
			if(storageItem.getTypeOfStorage() != null) {
				typeOfStorage = storageItem.getTypeOfStorage().getName();
			}
			
			storageItemList.add(new StorageItemModel(storageItem.getId(), storageItem.getName(), storageItem.getAmount(), storageItem.getAmountExpected(),
					storageItem.getStoredType().getStorageTypeName(), storageItem.getAdditionalInfo(), storageItem.getVendor(), typeOfStorage, 
					storageItem.getTypeOfStorage(), storageItem.getStoredType()));
		});
	}
	
}
