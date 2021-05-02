package com.southwicksstorage.southwicksstorage.controllers.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.models.NotificationModel;
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
	
	@RequestMapping(value = "/notification/readNotification", method = RequestMethod.GET)
	@ResponseBody
	public NotificationModel getNotificationById(Integer id) {
		NotificationModelEntity notification = notiRepo.findById(id).get();
		
		notification.setRead(true);
		notiRepo.save(notification);
		
		NotificationModel returnModel = new NotificationModel(id, notification.getNotificationType().getType(),
				notification.getMessage().getMessage(), notification.getIsRead());
		return returnModel;
	}
	
	@RequestMapping(value = "/notification/findById", method = RequestMethod.GET)
	@ResponseBody
	public NotificationModel findNotificationById(Integer id) {
		NotificationModelEntity notification = notiRepo.findById(id).get();
		
		NotificationModel returnModel = new NotificationModel(id, notification.getNotificationType().getType(),
				notification.getMessage().getMessage(), notification.getIsRead());
		return returnModel;
	}
	
	@RequestMapping(value = "/notification/getNewNotifications", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<NotificationModel> getAllNotReadNotifications() {
		Authentication getSecContext = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails getUserDetails = (CustomUserDetails) getSecContext.getPrincipal();
		List<NotificationModel> returnList = new ArrayList<NotificationModel>();
		if(userRepo.findById(getUserDetails.getId()).isPresent()) {
			UserModelEntity userModel = userRepo.findById(getUserDetails.getId()).get();
			List<NotificationModelEntity> getAllNotificationsByUserId = notiRepo.findAllByUserModelAndIsRead(userModel, false);
			
			for(int index = 0; index < getAllNotificationsByUserId.size(); index++) {
				NotificationModelEntity addNoti = getAllNotificationsByUserId.get(index);
				
				returnList.add(new NotificationModel(addNoti.getId(), addNoti.getNotificationType().getType(), 
						addNoti.getMessage().getMessage(), addNoti.getIsRead()));
			}
		}
		
		return returnList;
	}
	
	@RequestMapping(value = "/notification/deleteNotification", method = RequestMethod.POST)
	@ResponseBody
	public List<NotificationModel> getAllNotReadNotificationsAfterDelete(Integer id) {
		Authentication getSecContext = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails getUserDetails = (CustomUserDetails) getSecContext.getPrincipal();
		UserModelEntity userModel = userRepo.findById(getUserDetails.getId()).get();
		List<NotificationModel> returnList = new ArrayList<NotificationModel>();
		NotificationModelEntity findNotificationToDelete = notiRepo.findById(id).get();
		
		notiRepo.delete(findNotificationToDelete);
		
		List<NotificationModelEntity> getAllNotificationsByUserId = notiRepo.findAllByUserModelAndIsRead(userModel, false);
		for(int index = 0; index < getAllNotificationsByUserId.size(); index++) {
			NotificationModelEntity addNoti = getAllNotificationsByUserId.get(index);
			
			returnList.add(new NotificationModel(addNoti.getId(), addNoti.getNotificationType().getType(), 
					addNoti.getMessage().getMessage(), addNoti.getIsRead()));
		}
		
		return returnList;
	}
	
}
