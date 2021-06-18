/**
 * 
 */
package com.southwicksstorage.southwicksstorage.integration.selenium.configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author kyle
 *
 */
@EnableJpaRepositories(basePackages = {"com.southwicksstorage.southwicksstorage.repositories"})
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Profile("selenium-test")
public class H2SeleniumSetup {
	
}
