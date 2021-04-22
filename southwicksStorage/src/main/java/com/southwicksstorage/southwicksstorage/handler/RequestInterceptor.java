package com.southwicksstorage.southwicksstorage.handler;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.NotificationMessages;
import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.repositories.NotificationDao;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

@Component
public class RequestInterceptor implements HandlerInterceptor {
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private NotificationDao notiRepo;
	
	@Autowired
	private UserDao userRepo;
	
	private static Logger logger = LogManager.getLogger(RequestInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserModelEntity user = null;
		Optional<UserModelEntity> userOptional = null;
		List<NotificationModelEntity> notifications = null;
		int notificationWithPriority = 0;
		
		/**
		 * Only the following operations can be performed if the user is logged in
		 */
		if(isUserLoggedIn()) {
			userOptional = userRepo.findById(getUserDetailsLogged().getId());
			
			/*
			 * If the user is found lets add the notification that if the password is still default change it
			 * Don't add more than one
			 */
			if(userOptional.isPresent()) {
				user = userOptional.get();
				
				/* So if the password is the default password lets add a notification */
				if(bCryptPasswordEncoder.matches(Constants.DEFAULT_PASSWORD, user.getPassword())) {
					Optional<NotificationModelEntity> notificationExists = notiRepo.findByMessageAndUserModel(NotificationMessages.DEFAULT_PASSWORD_MESSAGE, user);
					
					if(notificationExists.isEmpty()) {
					
						NotificationModelEntity notificationEntity = new NotificationModelEntity(NotificationMessages.DEFAULT_PASSWORD_MESSAGE, 
								NotificationTypes.WARNING, false, user);
						notiRepo.saveAndFlush(notificationEntity);
						
					}
				/*
				 * If not lets remove that notification
				 */
				} else {
					Optional<NotificationModelEntity> notificationExists = notiRepo.findByMessageAndUserModel(NotificationMessages.DEFAULT_PASSWORD_MESSAGE, user);
					
					if(notificationExists.isPresent()) {
					
						NotificationModelEntity notificationEntity = notificationExists.get();
						notiRepo.delete(notificationEntity);
						
					}
				}
			}
			
			/*
			 * Find the highest priority badge for this user and add the badge to the navbar
			 */
			if(userOptional.isPresent()) {
				user = userOptional.get();
				notifications = notiRepo.findAllByUserModelAndIsRead(user, false);
				
				if(notifications != null) {
					for(int index = 0; index < notifications.size(); index++) {
						if(notifications.get(index).getNotificationType().getPriority() > notificationWithPriority) {
							notificationWithPriority = notifications.get(index).getNotificationType().getPriority();
						}
					}
				}
				
				if(notificationWithPriority > 0) {
					request.setAttribute("notificationBadge", NotificationTypes.findTypeByPriority(notificationWithPriority));
				}
			}
			
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
		
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		
		
	}
	
	public static boolean isUserLoggedIn() {
	    try {
	        return !SecurityContextHolder.getContext().getAuthentication()
	          .getName().equals("anonymousUser");
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	public static CustomUserDetails getUserDetailsLogged() {
		CustomUserDetails returnDetails = null;
		if(isUserLoggedIn()) {
			returnDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		return returnDetails;
	}
	
	
	
}
