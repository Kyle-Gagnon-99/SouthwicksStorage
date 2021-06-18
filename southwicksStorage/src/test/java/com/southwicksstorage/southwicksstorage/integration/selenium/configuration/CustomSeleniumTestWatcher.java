/**
 * 
 */
package com.southwicksstorage.southwicksstorage.integration.selenium.configuration;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.southwicksstorage.southwicksstorage.integration.selenium.BaseSeleniumTests;

/**
 * @author kyle
 *
 */
public class CustomSeleniumTestWatcher implements TestWatcher {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {
		log.error("Test {} in test case {} failed with reasoning {}", context.getTestMethod().get().getName(),
				context.getTestClass().get().getSimpleName(), cause.getMessage());
		BaseSeleniumTests.takeScreenshotOfTest(context.getTestClass().get().getSimpleName(), context.getTestMethod().get().getName(), cause);
		CommonSeleniumMethods.logout(BaseSeleniumTests.getWebDriver());
	}
	
	@Override
	public void testSuccessful(ExtensionContext context) {
		log.info("Test {} completed successfully", context.getTestMethod().get().getName());
		CommonSeleniumMethods.logout(BaseSeleniumTests.getWebDriver());
	}

}
