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

import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.services.StandService;

/**
 * @author kyle
 *
 */
@Controller
public class ViewStandController {

	@Autowired
	private StandService standService;
	
	private Logger log = LoggerFactory.getLogger(ViewStandController.class);
	
	@RequestMapping(value = "/view/stand", method = RequestMethod.GET)
	public ModelAndView getViewStand(Model model) {
		model.addAttribute("standList", standService.findAll());		
		return new ModelAndView("view/viewstand.html");
	}
	
	@RequestMapping(value = "/view/stand/deleteStand", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteStandById(Integer id) {
		
		try {
			standService.delete(standService.findById(id));
		} catch(Exception e) {
			log.error("Can't delete stand {} as it still has items associated to it which violates a foreign key constraint", standService.findById(id).getName());
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value = "/view/stand/editStand", method = RequestMethod.POST)
	@ResponseBody
	public List<StandEntity> editStand(Integer id, String name, String additionalInfo) {
		StandEntity retreivedStand = standService.findById(id);
		retreivedStand.setName(name);
		retreivedStand.setAdditionalInfo(additionalInfo);
		standService.save(retreivedStand);
		return standService.findAll();
	}
	
	@RequestMapping(value = "/view/stand/getAllStands", method = RequestMethod.POST)
	@ResponseBody
	public List<StandEntity> getAllStands() {
		return standService.findAll();
	}
	
	
}
