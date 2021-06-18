/**
 * 
 */
package com.southwicksstorage.southwicksstorage.integration.selenium.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.integration.selenium.BaseSeleniumTests;
import com.southwicksstorage.southwicksstorage.integration.selenium.configuration.SeleniumConstants;

/**
 * @author kyle
 * Tests to test the basic login, reset password, and logout features
 * All of these will be created as methods since logging in, resetting passwords, and logging out are common operations on most tests
 */
public class LoginResetPasswordAndHomeTest extends BaseSeleniumTests {
	
	/**
	 * Since we are testing the login feature we want to create a blank slate so that we can test each step
	 */
	@AfterEach
	public void setupTestData() {
		UserModelEntity testManager = userService.findByUsername(SeleniumConstants.TEST_USERNAME_MANAGER);
		UserModelEntity testTM = userService.findByUsername(SeleniumConstants.TEST_USERNAME_TM);
		
		if(bCryptPasswordEncoder.matches(SeleniumConstants.TEST_DEFAULT_PASSWORD, testManager.getPassword())) {
			testManager.setPassword(bCryptPasswordEncoder.encode(Constants.DEFAULT_PASSWORD));
			userService.save(testManager);
		}
		
		if(bCryptPasswordEncoder.matches(SeleniumConstants.TEST_DEFAULT_PASSWORD, testTM.getPassword())) {
			testTM.setPassword(bCryptPasswordEncoder.encode(Constants.DEFAULT_PASSWORD));
			userService.save(testTM);
		}
	}
	
	@Test
	public void testFirstTimeLoginAsManagerWithoutResetting() {
		webDriver.get(getLocalhost());
		
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_MANAGER);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		WebElement currentPasswordField = webDriver.findElement(By.id("currentPassword"));
		WebElement newPasswordField = webDriver.findElement(By.id("newPassword"));
		WebElement retypeNewPasswordField = webDriver.findElement(By.id("retypeNewPassword"));
		
		assertThat(currentPasswordField).isNotEqualTo(null);
		assertThat(newPasswordField).isNotEqualTo(null);
		assertThat(retypeNewPasswordField).isNotEqualTo(null);
	}
	
	@Test
	public void testFirstTimeLoginAsTeamMemberWithoutResetting() {
		webDriver.get(getLocalhost());
		
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		WebElement currentPasswordField = webDriver.findElement(By.id("currentPassword"));
		WebElement newPasswordField = webDriver.findElement(By.id("newPassword"));
		WebElement retypeNewPasswordField = webDriver.findElement(By.id("retypeNewPassword"));
		
		assertThat(currentPasswordField).isNotEqualTo(null);
		assertThat(newPasswordField).isNotEqualTo(null);
		assertThat(retypeNewPasswordField).isNotEqualTo(null);
	}
	
	@Test
	public void testFirstTimeLoginAsManagerWithReset() {
		webDriver.get(getLocalhost());
		
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_MANAGER);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		// Reset password
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("retypeNewPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submit")).click();
		
		WebElement usernameField = webDriver.findElement(By.id("username"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		
		assertThat(usernameField).isNotEqualTo(null);
		assertThat(passwordField).isNotEqualTo(null);
	}
	
	@Test
	public void testFirstTimeLoginAsTeamMemberWithReset() {
		webDriver.get(getLocalhost());
		
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		// Reset password
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("retypeNewPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submit")).click();
		
		WebElement usernameField = webDriver.findElement(By.id("username"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		
		assertThat(usernameField).isNotEqualTo(null);
		assertThat(passwordField).isNotEqualTo(null);
	}
	
	@Test
	public void testFirstTimeLoginAsManagerWithResetAndLogin() {
		webDriver.get(getLocalhost());
		
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_MANAGER);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		// Reset password
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("retypeNewPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submit")).click();
		
		// Log back in
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_MANAGER);
		webDriver.findElement(By.id("password")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		assertThat(webDriver.getCurrentUrl()).isEqualTo(getLocalhost() + "/");
		
	}
	
	@Test
	public void testFirstTimeLoginAsTeamMemberWithResetAndLogin() {
		webDriver.get(getLocalhost());
		
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		// Reset password
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("retypeNewPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submit")).click();
		
		// Log back in
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
		webDriver.findElement(By.id("password")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		assertThat(webDriver.getCurrentUrl()).isEqualTo(getLocalhost() + "/");
		
	}
	
	@Test
	public void testFirstTimeLoginAsManagerResetLoginAndLogout() {
		webDriver.get(getLocalhost());
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_MANAGER);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		// Reset password
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("retypeNewPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submit")).click();
		
		// Log back in
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_MANAGER);
		webDriver.findElement(By.id("password")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		// Logout
		webDriver.findElement(By.id("userDropdown")).click();
		webDriver.findElement(By.id("logoutButton")).click();
		
		// We have logged out successfully
		assertThat(webDriver.getCurrentUrl()).isEqualTo(getLocalhost() + "/auth/login?logout");
	}
	
	@Test
	public void testFirstTimeLoginAsTeamMemberResetLoginAndLogout() {
		webDriver.get(getLocalhost());
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		// Reset password
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("retypeNewPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submit")).click();
		
		// Log back in
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
		webDriver.findElement(By.id("password")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		// Logout
		webDriver.findElement(By.id("userDropdown")).click();
		webDriver.findElement(By.id("logoutButton")).click();
		
		// We have logged out successfully
		assertThat(webDriver.getCurrentUrl()).isEqualTo(getLocalhost() + "/auth/login?logout");
	}
	
	@Test
	public void testResetPasswordWithNoFieldsFilledOut() {
		webDriver.get(getLocalhost());
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		// Submit the form with no feilds filled out
		webDriver.findElement(By.tagName("form")).submit();
		
		// List of the expected outcome
		List<String> expectedResult = new ArrayList<String>(Arrays.asList("The password you entered does not match your current password",
				"New password can not be empty", "Passwords must match"));	
		List<String> retreivedResult = new ArrayList<String>();
		
		// Get the errors and add them to the result list
		webDriver.findElements(By.xpath("//div[@class='invalid-feedback']")).stream().forEach((element) -> {
			retreivedResult.add(element.getAttribute("innerHTML"));
		});
		
		assertThat(retreivedResult).isEqualTo(expectedResult);
	}
	
	@Test
	public void testResetPasswordWithCurrentPasswordIncorrectAndOtherFieldsEmpty() {
		webDriver.get(getLocalhost());
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		webDriver.findElement(By.id("currentPassword")).sendKeys("Invalid");
		
		webDriver.findElement(By.tagName("form")).submit();
		
		// List of the expected outcome
		List<String> expectedResult = new ArrayList<String>(Arrays.asList("The password you entered does not match your current password",
				"New password can not be empty", "Passwords must match"));	
		List<String> retreivedResult = new ArrayList<String>();
		
		// Get the errors and add them to the result list
		webDriver.findElements(By.xpath("//div[@class='invalid-feedback']")).stream().forEach((element) -> {
			retreivedResult.add(element.getAttribute("innerHTML"));
		});
		
		assertThat(retreivedResult).isEqualTo(expectedResult);
		
	}
	
	@Test
	public void testResetPasswordWithCurrentPasswordCorrectAndNewPasswordInvalid() {
		webDriver.get(getLocalhost());
		// Login
		webDriver.findElement(By.id("username")).sendKeys(SeleniumConstants.TEST_USERNAME_TM);
		webDriver.findElement(By.id("password")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("submitForm")).click();
		
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys("invalid");
		webDriver.findElement(By.tagName("form")).submit();
		
		// List of the expected outcome
		List<String> expectedResult = new ArrayList<String>(Arrays.asList("Password must be 8 or more characters in length, "
				+ "Password must contain 1 or more uppercase characters, Password must contain 1 or more digit characters, "
				+ "Password must contain 1 or more special characters", 
				"Passwords must match"));	
		List<String> retreivedResult = new ArrayList<String>();
		
		for(WebElement element : webDriver.findElements(By.xpath("//div[@class='invalid-feedback']"))) {
			retreivedResult.add(element.getAttribute("innerHTML"));
		}
		
		// Check
		assertThat(retreivedResult).isEqualTo(expectedResult);
		
		// Reset form (kind of a hack but I like it)
		webDriver.findElement(By.xpath("/html/body/main/div/div/div/div/div[2]/div/div/form/div[4]/a/button")).click();
		expectedResult.clear();
		retreivedResult.clear();
		
		// Try password with 8 characters but nothing else
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys("invalidbutwitheight");
		webDriver.findElement(By.tagName("form")).submit();
		
		// List of the expected outcome
		expectedResult = new ArrayList<String>(Arrays.asList("Password must contain 1 or more uppercase characters, "
				+ "Password must contain 1 or more digit characters, "
				+ "Password must contain 1 or more special characters", 
				"Passwords must match"));	
		retreivedResult = new ArrayList<String>();
		
		// Get the errors and add them to the result list
		for(WebElement element : webDriver.findElements(By.xpath("//div[@class='invalid-feedback']"))) {
			retreivedResult.add(element.getAttribute("innerHTML"));
		}
		
		// Check
		assertThat(retreivedResult).isEqualTo(expectedResult);
		
		// Reset form (kind of a hack but I like it)
		webDriver.findElement(By.xpath("/html/body/main/div/div/div/div/div[2]/div/div/form/div[4]/a/button")).click();
		expectedResult.clear();
		retreivedResult.clear();
		
		// Try password with eight characters and a digit but not an uppercase or special character
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys("invalidbutwith8anddigit");
		webDriver.findElement(By.tagName("form")).submit();
		
		// List of the expected outcome
		expectedResult = new ArrayList<String>(Arrays.asList("Password must contain 1 or more uppercase characters, "
				+ "Password must contain 1 or more special characters", 
				"Passwords must match"));	
		retreivedResult = new ArrayList<String>();
		
		// Get the errors and add them to the result list
		for(WebElement element : webDriver.findElements(By.xpath("//div[@class='invalid-feedback']"))) {
			retreivedResult.add(element.getAttribute("innerHTML"));
		}
		
		// Check
		assertThat(retreivedResult).isEqualTo(expectedResult);
		
		// Reset form (kind of a hack but I like it)
		webDriver.findElement(By.xpath("/html/body/main/div/div/div/div/div[2]/div/div/form/div[4]/a/button")).click();
		expectedResult.clear();
		retreivedResult.clear();
		
		// Try password with eight characters, a digit, and an uppercase but no special character
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys("Everythingbutwithsp3cialchar");
		webDriver.findElement(By.tagName("form")).submit();
		
		// List of the expected outcome
		expectedResult = new ArrayList<String>(Arrays.asList("Password must contain 1 or more special characters", 
				"Passwords must match"));	
		retreivedResult = new ArrayList<String>();
		
		// Get the errors and add them to the result list
		for(WebElement element : webDriver.findElements(By.xpath("//div[@class='invalid-feedback']"))) {
			retreivedResult.add(element.getAttribute("innerHTML"));
		}
		
		// Check
		assertThat(retreivedResult).isEqualTo(expectedResult);
		
		// Reset form (kind of a hack but I like it)
		webDriver.findElement(By.xpath("/html/body/main/div/div/div/div/div[2]/div/div/form/div[4]/a/button")).click();
		expectedResult.clear();
		retreivedResult.clear();
		
		// A valid password but retyping does not match
		webDriver.findElement(By.id("currentPassword")).sendKeys(Constants.DEFAULT_PASSWORD);
		webDriver.findElement(By.id("newPassword")).sendKeys(SeleniumConstants.TEST_DEFAULT_PASSWORD);
		webDriver.findElement(By.tagName("form")).submit();
		
		// List of the expected outcome
		expectedResult = new ArrayList<String>(Arrays.asList("Passwords must match"));	
		retreivedResult = new ArrayList<String>();
		
		// Get the errors and add them to the result list
		for(WebElement element : webDriver.findElements(By.xpath("//div[@class='invalid-feedback']"))) {
			retreivedResult.add(element.getAttribute("innerHTML"));
		}
		
		// Check
		assertThat(retreivedResult).isEqualTo(expectedResult);
	}
	
}
