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
import com.southwicksstorage.southwicksstorage.services.NotificationService;
import com.southwicksstorage.southwicksstorage.services.UserService;

@Controller
public class NotificationController {

	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/notification/notification", method = RequestMethod.GET)
	public ModelAndView getNotificationView(Model model) {
		
		Authentication getSecContext = SecurityContextHolder.getContext().getAuthentication();
		
		if(!getSecContext.getName().equals(Constants.NOT_LOGGED_IN)) {
			CustomUserDetails getUserDetails = (CustomUserDetails) getSecContext.getPrincipal();
			
			if(userService.findById(getUserDetails.getId()) != null) {
				UserModelEntity user = userService.findById(getUserDetails.getId());
				List<NotificationModelEntity> userNotifications = notiService.findNotisByUser(user);
				
				model.addAttribute("userNotifications", userNotifications);
			}
		}
		
		
		
		return new ModelAndView("notification/notification.html");
	}
	
	@RequestMapping(value = "/notification/readNotification", method = RequestMethod.GET)
	@ResponseBody
	public NotificationModel getNotificationById(Integer id) {
		NotificationModelEntity notification = notiService.findById(id);
		
		notification.setRead(true);
		notiService.save(notification);
		
		NotificationModel returnModel = new NotificationModel(id, notification.getNotificationType().getType(),
				notification.getMessage().getMessage(), notification.getIsRead());
		return returnModel;
	}
	
	@RequestMapping(value = "/notification/findById", method = RequestMethod.GET)
	@ResponseBody
	public NotificationModel findNotificationById(Integer id) {
		NotificationModelEntity notification = notiService.findById(id);
		
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
		if(userService.findById(getUserDetails.getId()) != null) {
			UserModelEntity userModel = userService.findById(getUserDetails.getId());
			List<NotificationModelEntity> getAllNotificationsByUserId = notiService.findNotisByUserandIsRead(userModel, false);
			
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
		UserModelEntity userModel = userService.findById(getUserDetails.getId());
		List<NotificationModel> returnList = new ArrayList<NotificationModel>();
		NotificationModelEntity findNotificationToDelete = notiService.findById(id);
		
		findNotificationToDelete.setIsVisible(false);
		
		List<NotificationModelEntity> getAllNotificationsByUserId = notiService.findNotisByUserIsReadAndIsVisible(userModel, false, true);
		for(int index = 0; index < getAllNotificationsByUserId.size(); index++) {
			NotificationModelEntity addNoti = getAllNotificationsByUserId.get(index);
			
			returnList.add(new NotificationModel(addNoti.getId(), addNoti.getNotificationType().getType(), 
					addNoti.getMessage().getMessage(), addNoti.getIsRead()));
		}
		
		return returnList;
	}
	
}
