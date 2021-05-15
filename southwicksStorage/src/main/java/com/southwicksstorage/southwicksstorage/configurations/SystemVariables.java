/**
 * 
 */
package com.southwicksstorage.southwicksstorage.configurations;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.southwicksstorage.southwicksstorage.constants.SystemSettingsName;
import com.southwicksstorage.southwicksstorage.repositories.SystemSettingsDao;

/**
 * @author kyle
 *
 */
@Component
@Order(value = 2)
public class SystemVariables implements ApplicationRunner {

	private static SystemSettingsDao systemSettingsRepo;
	
	public static double lowStandThreshold;
	public static double emptyStandThreshold;
	
	public SystemVariables(SystemSettingsDao systemSettingsRepo) {
		SystemVariables.systemSettingsRepo = systemSettingsRepo;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		lowStandThreshold = Double.parseDouble(systemSettingsRepo.findBySettingsName(SystemSettingsName.LOW_THRESHOLD).get().getSettingsValue());
		emptyStandThreshold = Double.parseDouble(systemSettingsRepo.findBySettingsName(SystemSettingsName.OUT_THRESHOLD).get().getSettingsValue());
	}
	
	public static void setLowStandThresdhold(double lowStandThreshold) {
		SystemVariables.lowStandThreshold = lowStandThreshold;
	}
	
	public static void setEmptyStandThreshold(double emptyStandThreshold) {
		SystemVariables.emptyStandThreshold = emptyStandThreshold;
	}
	
	public static void updateSystemVariables() {
		lowStandThreshold = Double.parseDouble(systemSettingsRepo.findBySettingsName(SystemSettingsName.LOW_THRESHOLD).get().getSettingsValue());
		emptyStandThreshold = Double.parseDouble(systemSettingsRepo.findBySettingsName(SystemSettingsName.OUT_THRESHOLD).get().getSettingsValue());
	}

}
