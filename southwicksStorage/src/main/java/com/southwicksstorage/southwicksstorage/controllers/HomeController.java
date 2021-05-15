package com.southwicksstorage.southwicksstorage.controllers;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.configurations.SystemVariables;
import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.NotificationMessages;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.models.attribute.Modal;
import com.southwicksstorage.southwicksstorage.repositories.NotificationDao;
import com.southwicksstorage.southwicksstorage.repositories.NotificationMessageDao;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Controller
public class HomeController {
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDao userRepo;
	
	@Autowired
	private NotificationDao notiRepo;
	
	@Autowired
	private NotificationMessageDao notiMessageRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndex(Model model, Authentication auth, Principal authPrin) {
		CustomUserDetails userCred = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String password = userCred.getPassword();
		/* Let's check to see if the user has the default password 
		 * If it does let's create a notification for the user to change their password
		 */
		
		if(bCryptPasswordEncoder.matches(Constants.DEFAULT_PASSWORD, password)) {
			Optional<UserModelEntity> userOptional = userRepo.findByUsername(userCred.getUsername());
			
			if(userOptional.isPresent()) {
				UserModelEntity user = userOptional.get();
				
				Optional<NotificationModelEntity> notificationExists = notiRepo.findByMessageAndUserModel(notiMessageRepo.findByMessage(NotificationMessages.DEFAULT_PASSWORD_MESSAGE.getMessage()).get(), user);
				
				if(notificationExists.isPresent()) {
					NotificationModelEntity notificationEntity = notificationExists.get();
					model.addAttribute("modal", new Modal("true", notificationEntity.getNotificationType().getType().toLowerCase(), 
							"Change password", notificationEntity.getMessage().getMessage()));
					
				}
			}
		}
		
		String lowValue = String.valueOf(SystemVariables.lowStandThreshold * 100) + "%";
		String emptyValue = String.valueOf(SystemVariables.emptyStandThreshold * 100) + "%";
		
		model.addAttribute("lowThresholdValue", lowValue);
		model.addAttribute("emptyThresholdValue", emptyValue);
		
		return new ModelAndView("index.html");
	}

}
