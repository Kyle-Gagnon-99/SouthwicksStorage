/**
 * 
 */
package com.southwicksstorage.southwicksstorage.integration.selenium.configuration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.services.UserService;

/**
 * @author kyle
 * 
 * Common methods to be used in our selenium testing such as logging in, resetting passwords, and logging out
 *
 */
public class CommonSeleniumMethods {
	
	private static Logger log = LoggerFactory.getLogger(CommonSeleniumMethods.class);
	
	/**
	 * Login as a test manager. If the password we have in the database is Password123! then login and reset otherwise login as the test manager
	 * @param url The url to login into
	 * @param webDriver The webDriver to pass
	 * @param userService The user service to pass
	 * @param passwordEncoder The password encoder to pass
	 */
	public static void loginAsManager(String url, WebDriver webDriver, UserService userService, PasswordEncoder passwordEncoder) {
		webDriver.get(url);
		
		UserModelEntity getForPassword = userService.findByUsername(SeleniumConstants.TEST_USERNAME_MANAGER);
		
		if(passwordEncoder.matches(Constants.DEFAULT_PASSWORD, getForPassword.getPassword())) {
			webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_MANAGER);
			webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
			webDriver.findElement(By.id("submitForm")).click();
			resetPassword(webDriver);
			webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_MANAGER);
			webDriver.findElement(By.id("password")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
			webDriver.findElement(By.id("submitForm")).click();
			log.info("Logging in first time as manager with username {} and password {} at url {}", 
					SeleniumConstants.TEST_USERNAME_MANAGER, SeleniumConstants.TEST_DEFAULT_PASSWORD, url);
		} else {
			webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_MANAGER);
			webDriver.findElement(By.id("password")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
			webDriver.findElement(By.id("submitForm")).click();
			log.info("Logging in as manager with username {} and password {} at url {}", 
					SeleniumConstants.TEST_USERNAME_MANAGER, SeleniumConstants.TEST_DEFAULT_PASSWORD, url);
		}
		
	}
	
	/**
	 * Login as a test team member. If the password in the H2 database is Password123! then login and reset password otherwise login as the test team member
	 * @param url The url to login into
	 * @param webDriver The webDriver to pass
	 * @param userService The user service to pass
	 * @param passwordEncoder The password encoder to pass
	 */
	public static void loginAsTeamMember(String url, WebDriver webDriver, UserService userService, PasswordEncoder passwordEncoder) {
		webDriver.get(url);
		
		UserModelEntity getForPassword = userService.findByUsername(SeleniumConstants.TEST_USERNAME_TM);
		
		if(passwordEncoder.matches(Constants.DEFAULT_PASSWORD, getForPassword.getPassword())) {
			webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
			webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
			webDriver.findElement(By.id("submitForm")).click();
			resetPassword(webDriver);
			webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
			webDriver.findElement(By.id("password")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
			webDriver.findElement(By.id("submitForm")).click();
			log.info("Logging in first time as team member with username {} and password {} at url {}", 
					SeleniumConstants.TEST_USERNAME_MANAGER, SeleniumConstants.TEST_DEFAULT_PASSWORD, url);
		} else {
			webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
			webDriver.findElement(By.id("password")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
			webDriver.findElement(By.id("submitForm")).click();
			log.info("Logging in as team member with username {} and password {} at url {}", 
					SeleniumConstants.TEST_USERNAME_TM, SeleniumConstants.TEST_DEFAULT_PASSWORD, url);
		}

	}
	
	/**
	 * A method to reset the password from the default password (Password123!) to the test password (Test123!)
	 * @param webDriver The web driver to pass and use
	 */
	public static void resetPassword(WebDriver webDriver) {
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("retypeNewPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submit")).click();
		log.info("Reseting password for the current user from {} to {}", Constants.DEFAULT_PASSWORD, SeleniumConstants.TEST_DEFAULT_PASSWORD);
	}
	
	/**
	 * Logout from any page as any user
	 * @param webDriver The web driver to pass and use
	 */
	public static void logout(WebDriver webDriver) {
		log.info("Logging out current user");
		try {
			webDriver.findElement(By.id("userDropdown")).click();
			webDriver.findElement(By.id("logoutButton")).click();
		} catch(NoSuchElementException noSuchElement) {
			log.error("Error trying to logout. The test may have already logged the user out, the logout wasn't on the page, or there was an error");
		} catch(NoSuchSessionException _ignore) {
			/* Ignore no such session */
		}
	}

}
