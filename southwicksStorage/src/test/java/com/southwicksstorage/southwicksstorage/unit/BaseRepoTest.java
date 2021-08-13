/**
 * 
 */
package com.southwicksstorage.southwicksstorage.unit;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.southwicksstorage.southwicksstorage.testconfigurations.UnitTestNameGenerator;
import com.southwicksstorage.southwicksstorage.unit.configuration.CustomUnitTestWatcher;

/**
 * @author Kyle Gagnon
 * Created On: 2021-08-13
 *
 */
@ExtendWith(CustomUnitTestWatcher.class)
@DisplayNameGeneration(UnitTestNameGenerator.class)
@DataJpaTest
public class BaseRepoTest {
	
	protected static Validator validator;
	
	@BeforeAll
	public static void initBeforeAllTests() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}

}
