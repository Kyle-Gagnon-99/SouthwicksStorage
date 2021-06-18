/**
 * 
 */
package com.southwicksstorage.southwicksstorage.configurations;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.constants.SystemSettingsName;
import com.southwicksstorage.southwicksstorage.services.NotificationMessageService;
import com.southwicksstorage.southwicksstorage.services.NotificationService;
import com.southwicksstorage.southwicksstorage.services.StandItemService;
import com.southwicksstorage.southwicksstorage.services.StandService;
import com.southwicksstorage.southwicksstorage.services.SystemSettingsService;
import com.southwicksstorage.southwicksstorage.services.UserService;

/**
 * @author kyle
 *
 */
@Component
@Order(value = 2)
@Profile("production")
public class SystemVariables implements ApplicationRunner {

	private static SystemSettingsService systemSettingsService;
	private StandService standService;
	private StandItemService standItemService;
	private UserService userService;
	private NotificationService notiService;
	private NotificationMessageService notiMessageService;
	
	public static float lowStandThreshold;
	public static float emptyStandThreshold;
	
	public SystemVariables(SystemSettingsService systemSettingsService, StandService standService, StandItemService standItemService, UserService userService, 
			NotificationService notiService, NotificationMessageService notiMessageService) {
		SystemVariables.systemSettingsService = systemSettingsService;
		this.standService = standService;
		this.standItemService = standItemService;
		this.userService = userService;
		this.notiService = notiService;
		this.notiMessageService = notiMessageService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		lowStandThreshold = Float.parseFloat(systemSettingsService.findBySettingsName(SystemSettingsName.LOW_THRESHOLD).getSettingsValue());
		emptyStandThreshold = Float.parseFloat(systemSettingsService.findBySettingsName(SystemSettingsName.OUT_THRESHOLD).getSettingsValue());
		
		/*
		 * NOTE: This is needed on startup but it is actually needed after we initialize the database and set the variables in this file
		 */
		CommonMethods.addAnyNotifications(userService.findAllByRole(Roles.MANAGER), standItemService.findAll(), notiService, notiMessageService);
		CommonMethods.removeAnyNotifications(standService, standItemService, userService, notiService, notiMessageService);
	}
	
	public static void setLowStandThresdhold(float lowStandThreshold) {
		SystemVariables.lowStandThreshold = lowStandThreshold;
	}
	
	public static void setEmptyStandThreshold(float emptyStandThreshold) {
		SystemVariables.emptyStandThreshold = emptyStandThreshold;
	}
	
	public static void updateSystemVariables() {
		lowStandThreshold = Float.parseFloat(systemSettingsService.findBySettingsName(SystemSettingsName.LOW_THRESHOLD).getSettingsValue());
		emptyStandThreshold = Float.parseFloat(systemSettingsService.findBySettingsName(SystemSettingsName.OUT_THRESHOLD).getSettingsValue());
	}

}
