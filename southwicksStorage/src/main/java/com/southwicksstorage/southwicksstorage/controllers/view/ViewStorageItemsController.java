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
import com.southwicksstorage.southwicksstorage.services.OrderReportService;
import com.southwicksstorage.southwicksstorage.services.StorageItemService;
import com.southwicksstorage.southwicksstorage.services.TypeOfStorageService;
import com.southwicksstorage.southwicksstorage.services.VendorService;

/**
 * @author kyle
 *
 */
@Controller
public class ViewStorageItemsController {

	@Autowired
	private StorageItemService storageItemService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private TypeOfStorageService tosService;
	
	@Autowired
	private OrderReportService orderReportService;
	
	private List<StorageItemModel> storageItemList = null;
	private Logger log = LoggerFactory.getLogger(ViewStorageItemsController.class);
	
	@RequestMapping(value = "/view/storageItem", method = RequestMethod.GET)
	public ModelAndView getViewStorageItem(Model model) {
		model.addAttribute("storageItemList", storageItemService.findAll());
		model.addAttribute("createStorageItemForm", new CreateStorageItemFormModel());
		List<VendorEntity> vendorList = vendorService.findAll();
		List<TypeOfStorageEntity> typeOfStorageList = tosService.findAll();
		
		if(vendorList.size() > 0) {
			model.addAttribute("vendorList", vendorList);
		}
		
		if(typeOfStorageList.size() > 0) {
			model.addAttribute("typeOfStorageList", typeOfStorageList);
		} else {
			model.addAttribute("typeOfStorageList", null);
		}
		
		return new ModelAndView("view/viewstorageitem.html");
	}
	
	@RequestMapping(value = "/view/storageItem/deleteItem", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteStorageItem(Integer id) {	
		try {
			storageItemService.delete(storageItemService.findById(id));
		} catch(Exception e) {
			log.error("Can not delete storage item {} because there are still stand items associated to this storage item (FK Constraint).", 
					storageItemService.findById(id).getName());
			return false;
		}
		
		updateStorageItemList();
		return true;
	}
	
	@RequestMapping(value = "/view/storageItem/editItem", method = RequestMethod.POST)
	@ResponseBody
	public StorageItemEntity editStorageItem(Integer id, String name, Integer amount, Integer amountExpected,
			String storedType, Integer vendor, Integer typeOfStorage, String additionalInfo) {
		
		StorageItemEntity item = storageItemService.findById(id);
		
		item.setName(name);
		item.setAmount(amount);
		item.setAmountExpected(amountExpected);
		item.setStoredType(StorageType.valueOf(storedType));
		item.setVendor(vendorService.findById(vendor));
		if(tosService.findById(typeOfStorage) != null) {
			item.setTypeOfStorage(tosService.findById(typeOfStorage));
		} else if(typeOfStorage == -1) {
			item.setTypeOfStorage(null);
		}
		item.setAdditionalInfo(additionalInfo);
		
		storageItemService.save(item);
		
		CommonMethods.addToOrderReport(item, orderReportService);
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
			typeOfStorage = tosService.findById(typeOfStorageId);
		}
		
		StorageItemEntity createStorageItem = new StorageItemEntity(name, amount, amountExpected, StorageType.valueOf(storedType),  additionalInfo,
				vendorService.findById(vendorId), typeOfStorage);
		
		try {
			storageItemService.save(createStorageItem);
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
		
		if(vendorService.findById(vendorId) == null) {
			log.error("vendorId was passed to the controller with an invalid value of {}", vendorId);
			return false;
		}
		
		return !storageItemService.existsByNameAndVendor(name, vendorService.findById(vendorId));
	}
	
	private void updateStorageItemList() {
		List<StorageItemEntity> storageList = storageItemService.findAll();
		
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
