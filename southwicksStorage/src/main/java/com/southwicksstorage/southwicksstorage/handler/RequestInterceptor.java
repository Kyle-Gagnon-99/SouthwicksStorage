package com.southwicksstorage.southwicksstorage.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
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
import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;
import com.southwicksstorage.southwicksstorage.services.NotificationService;
import com.southwicksstorage.southwicksstorage.services.UserService;

@Component
public class RequestInterceptor implements HandlerInterceptor {
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(RequestInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		UserModelEntity user = null;
		List<NotificationModelEntity> notifications = null;
		int notificationWithPriority = 0;
		
		/**
		 * Only the following operations can be performed if the user is logged in
		 */
		if(isUserLoggedIn()) {
			user = userService.findById(getUserDetailsLogged().getId());
			
			/*
			 * Find the highest priority badge for this user and add the badge to the navbar
			 */
			if(user != null) {
				notifications = notiService.findNotisByUserandIsRead(user, false);
				
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
		UserModelEntity user = null;
	
		if(!Constants.DEBUG_MODE) {
			if(isUserLoggedIn()) {
				user = userService.findById(getUserDetailsLogged().getId());
				
				/*
				 * If the user is found lets add the notification that if the password is still default change it
				 * Don't add more than one
				 */
				if(user != null) {		
					
					/*
					 * If the user's password is the default password
					 */
					if(bCryptPasswordEncoder.matches(Constants.DEFAULT_PASSWORD, user.getPassword())) {
						
						if(!request.getRequestURI().equals("/auth/resetpassword")) {
							try {
								response.sendRedirect("/auth/resetpassword");
							} catch(Exception e) {
								/* Ignore */
							}
							response.setStatus(Response.SC_OK);
						}
					}
					
				}
				
			}
		} else {
			logger.warn("RESETTING PASSWORD IS DISABLED DUE TO DEBUG MODE BEING ACTIVE. PLEASE ENSURE THIS IS NOT ENABLED IN PRODUCTION!");
		}
		
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
