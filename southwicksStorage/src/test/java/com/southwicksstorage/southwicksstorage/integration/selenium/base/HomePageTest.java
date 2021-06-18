/**
 * 
 */
package com.southwicksstorage.southwicksstorage.integration.selenium.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.southwicksstorage.southwicksstorage.integration.selenium.BaseSeleniumTests;
import com.southwicksstorage.southwicksstorage.integration.selenium.configuration.CommonSeleniumMethods;
import com.southwicksstorage.southwicksstorage.integration.selenium.configuration.SeleniumConstants;

/**
 * @author kyle
 *
 */
public class HomePageTest extends BaseSeleniumTests {

	@Test
	public void testHomePageNavbarForManagers() {
		CommonSeleniumMethods.loginAsManager(getLocalhost(), webDriver, userService, bCryptPasswordEncoder);
		String[] expectedNavBarElementsArray = {"Main", "View", "Create", "Notifications", "Reports", "Settings", "Home", "View", "Create", "Notifications",
				"Reports", "Settings", "Home", "Storage Items", "Stand Items", "Vendors", "Users", "Type of Storage", "Stands", "Audit Log", "User",
				"Vendor", "Storage Item", "Stand", "Type of Storage", "Notifications", "Order Report", "Stand Report", "Settings", "Reset Password"};
		List<String> navBarElements = new ArrayList<String>();
		List<String> expectedNavBarElements = Arrays.asList(expectedNavBarElementsArray);
		
		List<WebElement> headerElement = webDriver.findElements(By.className("sidebar-header"));
		List<WebElement> itemHeaderElements = webDriver.findElements(By.xpath("//li[@class='sidebar-item']/a/span"));
		List<WebElement> itemElements = webDriver.findElements(By.xpath("//li[@class='sidebar-item']/a[@class='sidebar-link']"));
		
		for(WebElement element : headerElement) {
			navBarElements.add(element.getText());
		}
		for(WebElement element : itemHeaderElements) {
			navBarElements.add(element.getAttribute("innerHTML"));
		}
		
		for(WebElement element : itemElements) {
			navBarElements.add(StringUtils.trim(element.getAttribute("innerHTML")));
		}
		
		assertThat(navBarElements).isEqualTo(expectedNavBarElements);
	}
	
	@Test
	public void testHomePageNavbarForTeamMembers() {
		CommonSeleniumMethods.loginAsTeamMember(getLocalhost(), webDriver, userService, bCryptPasswordEncoder);
		String[] expectedNavBarElementsArray = {"Main", "View", "Notifications", "Settings", "Home", "View", "Notifications", "Settings", "Home", "Stand Items", 
				"Notifications", "Settings", "Reset Password"};
		List<String> navBarElements = new ArrayList<String>();
		List<String> expectedNavBarElements = Arrays.asList(expectedNavBarElementsArray);
		
		List<WebElement> headerElement = webDriver.findElements(By.className("sidebar-header"));
		List<WebElement> itemHeaderElements = webDriver.findElements(By.xpath("//li[@class='sidebar-item']/a/span"));
		List<WebElement> itemElements = webDriver.findElements(By.xpath("//li[@class='sidebar-item']/a[@class='sidebar-link']"));
		
		for(WebElement element : headerElement) {
			navBarElements.add(element.getText());
		}
		for(WebElement element : itemHeaderElements) {
			navBarElements.add(element.getAttribute("innerHTML"));
		}
		
		for(WebElement element : itemElements) {
			navBarElements.add(StringUtils.trim(element.getAttribute("innerHTML")));
		}
		
		assertThat(navBarElements).isEqualTo(expectedNavBarElements);
		
	}
	
	@Test
	public void testHomePageEmployeeTable() {
		CommonSeleniumMethods.loginAsManager(getLocalhost(), webDriver, userService, bCryptPasswordEncoder);
		List<String> expectedTable = new ArrayList<String>(Arrays.asList(SeleniumConstants.TEST_FNAME_MANAGER, SeleniumConstants.TEST_FNAME_TM,
				SeleniumConstants.TEST_LNAME_MANAGER, SeleniumConstants.TEST_LNAME_TM));
		List<String> resultingTable = new ArrayList<String>();
		
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
			
		}
		
		List<WebElement> tableRowFirstName = webDriver.findElements(By.xpath("//table[@id='employeeTable']/tbody/tr/td[1]"));
		List<WebElement> tableRowLastName = webDriver.findElements(By.xpath("//table[@id='employeeTable']/tbody/tr/td[2]"));
		for(WebElement element : tableRowFirstName) {
			resultingTable.add(element.getAttribute("innerHTML"));
		}
		
		for(WebElement element : tableRowLastName) {
			resultingTable.add(element.getAttribute("innerHTML"));
		}
		
		assertThat(resultingTable).isEqualTo(expectedTable);
	}
	
	@Test
	public void testHomePageTeamMemberAndManagerHelpPageLinksShowForManager() {
		CommonSeleniumMethods.loginAsManager(getLocalhost(), webDriver, userService, bCryptPasswordEncoder);
		List<String> expectedResult = new ArrayList<String>(Arrays.asList(getLocalhost() + "/help/manager", getLocalhost() + "/help/teamMember"));
		List<String> result = new ArrayList<String>();
		
		List<WebElement> retreivedResult = webDriver.findElements(By.linkText("Guide"));
		
		for(WebElement element : retreivedResult) {
			result.add(element.getAttribute("href"));
		}
		
		assertThat(result).isEqualTo(expectedResult);
	}
	
	@Test
	public void testHomePageTeamMemberHelpPageLinksShowForTeamMember() {
		CommonSeleniumMethods.loginAsTeamMember(getLocalhost(), webDriver, userService, bCryptPasswordEncoder);
		
		List<String> expectedResult = new ArrayList<String>(Arrays.asList(getLocalhost() + "/help/teamMember"));
		List<String> result = new ArrayList<String>();
		
		List<WebElement> retreivedResult = webDriver.findElements(By.linkText("Guide"));
		
		for(WebElement element : retreivedResult) {
			result.add(element.getAttribute("href"));
		}
		
		assertThat(result).isEqualTo(expectedResult);
	}
	
}
