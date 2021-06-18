/**
 * 
 */
package com.southwicksstorage.southwicksstorage.unit.configuration;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kyle
 *
 */
public class CustomUnitTestWatcher implements TestWatcher {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void testFailed(ExtensionContext context, Throwable cause) {
		log.error("Test {} in test case {} failed with reasoning {}", context.getTestMethod().get().getName(),
				context.getTestClass().get().getSimpleName(), cause.getMessage());
	}
	
	@Override
	public void testSuccessful(ExtensionContext context) {
		log.info("Test {} completed successfully", context.getTestMethod().get().getName());
	}

}
