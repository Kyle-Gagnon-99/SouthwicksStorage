package com.southwicksstorage.southwicksstorage.controllers.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.models.NotificationModel;
import com.southwicksstorage.southwicksstorage.models.formModels.ReadNotificationForm;
import com.southwicksstorage.southwicksstorage.repositories.NotificationDao;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Controller
public class NotificationController {

	@Autowired
	private NotificationDao notiRepo;
	
	@Autowired
	private UserDao userRepo;
	
	@RequestMapping(value = "/notification/notification", method = RequestMethod.GET)
	public ModelAndView getNotificationView(Model model) {
		
		model.addAttribute("readNotificationForm", new ReadNotificationForm());
		Authentication getSecContext = SecurityContextHolder.getContext().getAuthentication();
		
		if(!getSecContext.getName().equals(Constants.NOT_LOGGED_IN)) {
			CustomUserDetails getUserDetails = (CustomUserDetails) getSecContext.getPrincipal();
			
			if(userRepo.findById(getUserDetails.getId()).isPresent()) {
				UserModelEntity user = userRepo.findById(getUserDetails.getId()).get();
				List<NotificationModelEntity> userNotifications = notiRepo.findAllByUserModel(user);
				
				model.addAttribute("userNotifications", userNotifications);
			}
		}
		
		
		
		return new ModelAndView("notification/notification.html");
	}
	
	@RequestMapping(value = "/notification/findById", method = RequestMethod.GET)
	@ResponseBody
	public NotificationModel getNotificationById(Integer id) {
		System.out.print(id);
		NotificationModelEntity notification = notiRepo.findById(id).get();
		NotificationModel returnModel = new NotificationModel(id, notification.getNotificationType().getType(),
				notification.getMessage().getMessage());
		return returnModel;
	}
	
	@RequestMapping(value = "/notification/readNotification", method = RequestMethod.POST)
	public ModelAndView postReadNotification(@ModelAttribute("readNotificationForm") ReadNotificationForm readNotification) {
		System.out.print(readNotification.getId());
		//NotificationModelEntity notification = notiRepo.findById(readNotification.getId()).get();
		
		//notification.setRead(true);
		//notiRepo.save(notification);
		
		return new ModelAndView("redirect:/notification/notification");
	}
	
}
