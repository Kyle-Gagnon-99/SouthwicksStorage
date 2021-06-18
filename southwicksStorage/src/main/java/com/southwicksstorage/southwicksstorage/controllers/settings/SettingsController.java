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
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.handler.RequestInterceptor;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.models.UserModel;
import com.southwicksstorage.southwicksstorage.services.SystemSettingsService;
import com.southwicksstorage.southwicksstorage.services.UserService;

/**
 * @author kyle
 *
 */
@Controller
public class SettingsController {

	@Autowired
	private SystemSettingsService systemSettingsService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public ModelAndView getSettingsPage(Model model) {
		model.addAttribute("lowThreshold", SystemVariables.lowStandThreshold * 100);
		model.addAttribute("emptyThreshold", SystemVariables.emptyStandThreshold * 100);
		return new ModelAndView("settings/settings.html");
	}
	
	@RequestMapping(value = "/settings/updateSettings", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateSettings(Integer lowThresholdInput, Integer emptyThresholdInput) {
		
		SystemSettingsEntity lowThreshold = systemSettingsService.findBySettingsName(SystemSettingsName.LOW_THRESHOLD);
		SystemSettingsEntity emptyThreshold = systemSettingsService.findBySettingsName(SystemSettingsName.OUT_THRESHOLD);
		
		lowThreshold.setSettingsValue(String.valueOf((lowThresholdInput / 100)));
		emptyThreshold.setSettingsValue(String.valueOf((emptyThresholdInput / 100)));
		
		systemSettingsService.save(lowThreshold);
		systemSettingsService.save(emptyThreshold);
		
		SystemVariables.updateSystemVariables();
		return true;
	}
	
	@RequestMapping(value = "/settings/updateAccountSettings", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateAccountSettings(String phoneNumber) {
		
		CustomUserDetails userDetails = null;
		
		if(RequestInterceptor.isUserLoggedIn()) {
			userDetails = RequestInterceptor.getUserDetailsLogged();
			
			UserModelEntity user = userService.findById(userDetails.getId());
			
			if(user != null) {
				user.setPhoneNumber(phoneNumber);
				userService.save(user);
			}
		}
		
		return true;
	}
	
	@RequestMapping(value = "/settings/getUserDetails", method = RequestMethod.GET)
	@ResponseBody
	public UserModel getUserDetails() {
		CustomUserDetails userDetails = null;
		UserModel returnUser = null;
		if(RequestInterceptor.isUserLoggedIn()) {
			userDetails = RequestInterceptor.getUserDetailsLogged();
			
			UserModelEntity user = userService.findById(userDetails.getId());
			
			if(user != null) {
				returnUser = new UserModel(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getRole().getRole(),
						user.getRole(), user.getPhoneNumber());
			}
		}
		
		return returnUser;
	}
	
}
