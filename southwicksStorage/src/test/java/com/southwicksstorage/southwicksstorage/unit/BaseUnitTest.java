/**
 * 
 */
package com.southwicksstorage.southwicksstorage.unit;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.southwicksstorage.southwicksstorage.SouthwicksStorageApplication;
import com.southwicksstorage.southwicksstorage.testconfigurations.UnitTestNameGenerator;
import com.southwicksstorage.southwicksstorage.unit.configuration.CustomUnitTestWatcher;

/**
 * @author kyle
 *
 */
@ExtendWith(CustomUnitTestWatcher.class)
@DisplayNameGeneration(UnitTestNameGenerator.class)
@ActiveProfiles(value = "unit-test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = SouthwicksStorageApplication.class)
public class BaseUnitTest {

	protected static Logger log = LoggerFactory.getLogger(BaseUnitTest.class);
	
}
