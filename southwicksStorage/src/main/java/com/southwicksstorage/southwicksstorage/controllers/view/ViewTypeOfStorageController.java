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
	
	@RequestMapping(value = "/view/typeOfStorage", method = RequestMethod.GET)
	public ModelAndView getViewTypeOfStorage(Model model) {
		List<TypeOfStorageEntity> storageList = repo.findAll();
		model.addAttribute("storageList", storageList);
		return new ModelAndView("view/viewtypeofstorage.html");
	}
	
	@RequestMapping(value = "/view/typeOfStorage/deleteStorage", method = RequestMethod.POST)
	@ResponseBody
	public List<TypeOfStorageEntity> deleteStorage(Integer id) {
		
		TypeOfStorageEntity retreivedStorage = repo.findById(id).get();
		
		repo.delete(retreivedStorage);
		
		return repo.findAll();
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
	
	@RequestMapping(value = "/view/typeOfStorage/findById", method = RequestMethod.GET)
	@ResponseBody
	public TypeOfStorageEntity getTypeOfStorageById(Integer id) {
		return repo.findById(id).get();
	}

	
	@RequestMapping(value = "/view/typeOfStorage/getAllTypesOfStorage", method = RequestMethod.GET)
	@ResponseBody
	public List<TypeOfStorageEntity> getAllTypesOfStorage() {
		return repo.findAll();
	}
}
