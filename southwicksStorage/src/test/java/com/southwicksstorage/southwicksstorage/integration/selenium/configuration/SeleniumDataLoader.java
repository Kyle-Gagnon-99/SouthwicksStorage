/**
 * 
 */
package com.southwicksstorage.southwicksstorage.integration.selenium.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.constants.SystemSettingsName;
import com.southwicksstorage.southwicksstorage.entities.SystemSettingsEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.services.SystemSettingsService;
import com.southwicksstorage.southwicksstorage.services.UserService;

/**
 * @author kyle
 *
 */
@Configuration
@Profile(value = { "selenium-test" })
public class SeleniumDataLoader implements ApplicationRunner {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private UserService userService;
	private SystemSettingsService systemSettingsService;
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public SeleniumDataLoader(UserService userService, SystemSettingsService systemSettingsService, PasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.systemSettingsService = systemSettingsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		userService.save(new UserModelEntity(SeleniumConstants.TEST_FNAME_MANAGER, SeleniumConstants.TEST_LNAME_MANAGER, SeleniumConstants.TEST_USERNAME_MANAGER, 
				bCryptPasswordEncoder.encode(Constants.DEFAULT_PASSWORD), Roles.MANAGER, null));
		userService.save(new UserModelEntity(SeleniumConstants.TEST_FNAME_TM, SeleniumConstants.TEST_LNAME_TM, SeleniumConstants.TEST_USERNAME_TM, 
				bCryptPasswordEncoder.encode(Constants.DEFAULT_PASSWORD), Roles.TEAM_MEMBER, null));
		
		if(systemSettingsService.findBySettingsName(SystemSettingsName.LOW_THRESHOLD) == null) {
			systemSettingsService.save(new SystemSettingsEntity(SystemSettingsName.LOW_THRESHOLD, Constants.LOW_THRESHOLD_DEFAULT));
		}
		if(systemSettingsService.findBySettingsName(SystemSettingsName.OUT_THRESHOLD) == null) {
			systemSettingsService.save(new SystemSettingsEntity(SystemSettingsName.OUT_THRESHOLD, Constants.OUT_THRESHOLD_DEFAULT));
		}
		
		log.info("Successfully loaded custom configuration");
	}

}
