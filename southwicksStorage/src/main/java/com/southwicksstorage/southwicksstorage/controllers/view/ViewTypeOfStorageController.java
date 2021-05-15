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

import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.repositories.TypeOfStorageDao;

/**
 * @author kyle
 *
 */
@Controller
public class ViewTypeOfStorageController {
	
	@Autowired
	private TypeOfStorageDao repo;
	
	private Logger log = LoggerFactory.getLogger(ViewTypeOfStorageController.class);
	
	@RequestMapping(value = "/view/typeOfStorage", method = RequestMethod.GET)
	public ModelAndView getViewTypeOfStorage(Model model) {
		List<TypeOfStorageEntity> storageList = repo.findAll();
		model.addAttribute("storageList", storageList);
		return new ModelAndView("view/viewtypeofstorage.html");
	}
	
	@RequestMapping(value = "/view/typeOfStorage/deleteStorage", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteStorage(Integer id) {
		
		TypeOfStorageEntity retreivedStorage = repo.findById(id).get();
		
		try {
			repo.delete(retreivedStorage);
		} catch(Exception e) {
			log.error("Can't delete type of storage {} as it still has items associated to it which violates a foreign key constraint", retreivedStorage.getName());
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value = "/view/typeOfStorage/editStorage", method = RequestMethod.POST)
	@ResponseBody
	public TypeOfStorageEntity editStorage(Integer id, String name, String additionalInfo) {
		
		TypeOfStorageEntity retreivedStorage = repo.findById(id).get();
		
		retreivedStorage.setName(name);
		retreivedStorage.setAdditionalInfo(additionalInfo);
		
		repo.save(retreivedStorage);
		
		return repo.findById(id).get();
	}
	
	@RequestMapping(value = "/view/typeOfStorage/getAllTypesOfStorage", method = RequestMethod.POST)
	@ResponseBody
	public List<TypeOfStorageEntity> getAllTypesOfStorage() {
		return repo.findAll();
	}
}
