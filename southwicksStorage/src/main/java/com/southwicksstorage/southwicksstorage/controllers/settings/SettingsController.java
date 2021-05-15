/**
 * 
 */
package com.southwicksstorage.southwicksstorage.controllers.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.configurations.SystemVariables;
import com.southwicksstorage.southwicksstorage.constants.SystemSettingsName;
import com.southwicksstorage.southwicksstorage.entities.SystemSettingsEntity;
import com.southwicksstorage.southwicksstorage.repositories.SystemSettingsDao;

/**
 * @author kyle
 *
 */
@Controller
public class SettingsController {

	@Autowired
	private SystemSettingsDao repo;
	
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public ModelAndView getSettingsPage(Model model) {
		model.addAttribute("lowThreshold", SystemVariables.lowStandThreshold * 100);
		model.addAttribute("emptyThreshold", SystemVariables.emptyStandThreshold * 100);
		return new ModelAndView("settings/settings.html");
	}
	
	@RequestMapping(value = "/settings/updateSettings", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateSettings(Integer lowThresholdInput, Integer emptyThresholdInput) {
		
		SystemSettingsEntity lowThreshold = repo.findBySettingsName(SystemSettingsName.LOW_THRESHOLD).get();
		SystemSettingsEntity emptyThreshold = repo.findBySettingsName(SystemSettingsName.OUT_THRESHOLD).get();
		
		lowThreshold.setSettingsValue(String.valueOf(((double)lowThresholdInput / 100)));
		emptyThreshold.setSettingsValue(String.valueOf(((double)emptyThresholdInput / 100)));
		
		repo.save(lowThreshold);
		repo.save(emptyThreshold);
		
		SystemVariables.updateSystemVariables();
		return true;
	}
	
}
