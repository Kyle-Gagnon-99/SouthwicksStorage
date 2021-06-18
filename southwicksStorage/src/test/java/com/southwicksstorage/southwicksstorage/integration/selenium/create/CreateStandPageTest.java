/**
 * 
 */
package com.southwicksstorage.southwicksstorage.integration.selenium.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.southwicksstorage.southwicksstorage.integration.selenium.BaseSeleniumTests;
import com.southwicksstorage.southwicksstorage.integration.selenium.configuration.CommonSeleniumMethods;

/**
 * @author kyle
 *
 */
public class CreateStandPageTest extends BaseSeleniumTests {

	@Test
	public void testVisitStandPageFromHomePageSuccess() {
		CommonSeleniumMethods.loginAsManager(getLocalhost(), webDriver, userService, bCryptPasswordEncoder);
		
		try {
			// Click on Create on the navbar then wait then click on Stand
			webDriver.findElement(By.xpath("//*[@id='sidebar']/div/ul/li[6]/a")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='create']/li[4]/a")));
			webDriver.findElement(By.xpath("//*[@id='create']/li[4]/a")).click();
		} catch(Exception e) {
			fail("Failed because of exception");
		}
		
		assertThat(webDriver.findElement(By.id("name"))).isNotEqualTo(null);
	}
	
	@Test
	public void testNoStandTableShowsInStandReport() {
		CommonSeleniumMethods.loginAsManager(getLocalhost(), webDriver, userService, bCryptPasswordEncoder);
		
		// Click on Report on the navbar then wait then click on Stand Report
		webDriver.findElement(By.xpath("//*[@id='sidebar']/div/ul/li[10]/a"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='reports']/li[2]/a")));
		webDriver.findElement(By.xpath("//*[@id='reports']/li[2]/a")).click();
		
		String shouldBeEmpty = webDriver.findElement(By.id("showReportByStand")).getAttribute("innerHTML");
		
		assertThat(StringUtils.isEmpty(shouldBeEmpty)).isEqualTo(true);
	}
	
}
