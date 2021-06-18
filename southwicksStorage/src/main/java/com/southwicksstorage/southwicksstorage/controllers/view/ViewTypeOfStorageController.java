/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.view;

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

import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.services.StorageItemService;
import com.southwicksstorage.southwicksstorage.services.TypeOfStorageService;

/**
 * @author kyle
 *
 */
@Controller
public class ViewTypeOfStorageController {
	
	@Autowired
	private TypeOfStorageService tosService;
	
	@Autowired
	private StorageItemService storageItemService;
	
	private Logger log = LoggerFactory.getLogger(ViewTypeOfStorageController.class);
	
	@RequestMapping(value = "/view/typeOfStorage", method = RequestMethod.GET)
	public ModelAndView getViewTypeOfStorage(Model model) {
		List<TypeOfStorageEntity> storageList = tosService.findAll();
		model.addAttribute("storageList", storageList);
		return new ModelAndView("view/viewtypeofstorage.html");
	}
	
	@RequestMapping(value = "/view/typeOfStorage/deleteStorage", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteStorage(Integer id) {
		
		TypeOfStorageEntity retreivedStorage = tosService.findById(id);
		
		try {
			
			List<StorageItemEntity> storageItemAssociatedtoStorage = storageItemService.findAllByTypeOfStorage(retreivedStorage);
			
			for(int index = 0; index < storageItemAssociatedtoStorage.size(); index++) {
				storageItemAssociatedtoStorage.get(index).setTypeOfStorage(null);
				storageItemService.save(storageItemAssociatedtoStorage.get(index));
			}
			
			tosService.delete(retreivedStorage);
		} catch(Exception e) {
			log.error("Can't delete type of storage {} as it still has items associated to it which violates a foreign key constraint", retreivedStorage.getName());
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value = "/view/typeOfStorage/editStorage", method = RequestMethod.POST)
	@ResponseBody
	public TypeOfStorageEntity editStorage(Integer id, String name, String additionalInfo) {
		
		TypeOfStorageEntity retreivedStorage = tosService.findById(id);
		
		retreivedStorage.setName(name);
		retreivedStorage.setAdditionalInfo(additionalInfo);
		
		tosService.save(retreivedStorage);
		
		return tosService.findById(id);
	}
	
	@RequestMapping(value = "/view/typeOfStorage/getAllTypesOfStorage", method = RequestMethod.POST)
	@ResponseBody
	public List<TypeOfStorageEntity> getAllTypesOfStorage() {
		return tosService.findAll();
	}
}
