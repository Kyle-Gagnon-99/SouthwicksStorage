/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
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
	
	@RequestMapping(value = "/view/storageItem", method = RequestMethod.GET)
	public ModelAndView getViewStorageItem(Model model) {
		return new ModelAndView("view/viewstorageitem.html");
	}
	
	@RequestMapping(value = "/view/storageItem/findById", method = RequestMethod.GET)
	@ResponseBody
	public StorageItemEntity findStorageItemById(Integer id) {
		return null;
	}
	
	@RequestMapping(value = "/view/storageItem/deleteItem", method = RequestMethod.POST)
	@ResponseBody
	public List<StorageItemEntity> deleteStorageItem(Integer id) {
		return null;
	}
	
	@RequestMapping(value = "/view/storageItem/editItem", method = RequestMethod.POST)
	@ResponseBody
	public List<StorageItemEntity> editStorageItem(Integer id, String name, Integer amount, Integer amountExpected,
			String storedIn, String additionalInfo, Integer vendor, Integer typeOfStorage) {
		return null;
	}
	
	@RequestMapping(value = "/view/storageItem/getAllStorageItems", method = RequestMethod.GET)
	@ResponseBody
	public List<StorageItemEntity> getAllItems() {
		return storageItemRepo.findAll();
	}
	
}
