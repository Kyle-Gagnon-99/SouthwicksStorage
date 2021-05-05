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

import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.repositories.StandDao;

/**
 * @author kyle
 *
 */
@Controller
public class ViewStandController {

	@Autowired
	private StandDao repo;
	
	@RequestMapping(value = "/view/stand", method = RequestMethod.GET)
	public ModelAndView getViewStand(Model model) {
		model.addAttribute("standList", repo.findAll());		
		return new ModelAndView("view/viewstand.html");
	}
	
	@RequestMapping(value = "/view/stand/findById", method = RequestMethod.GET)
	@ResponseBody
	public StandEntity findStandById(Integer id) {
		return repo.findById(id).get();
	}
	
	@RequestMapping(value = "/view/stand/deleteStand", method = RequestMethod.POST)
	@ResponseBody
	public List<StandEntity> deleteStandById(Integer id) {
		repo.delete(repo.findById(id).get());
		return repo.findAll();
	}
	
	@RequestMapping(value = "/view/stand/editStand", method = RequestMethod.POST)
	@ResponseBody
	public List<StandEntity> editStand(Integer id, String name, String additionalInfo) {
		StandEntity retreivedStand = repo.findById(id).get();
		retreivedStand.setName(name);
		retreivedStand.setAdditionalInfo(additionalInfo);
		repo.save(retreivedStand);
		return repo.findAll();
	}
	
	@RequestMapping(value = "/view/stand/getAllStands", method = RequestMethod.GET)
	@ResponseBody
	public List<StandEntity> getAllStands() {
		return repo.findAll();
	}
	
	
}
