/**
 * 
 */
package com.southwicksstorage.southwicksstorage.unit.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.southwicksstorage.southwicksstorage.repositories.VendorDao;
import com.southwicksstorage.southwicksstorage.unit.BaseRepoTest;

/**
 * @author Kyle Gagnon
 * Created On: 2021-07-18
 *
 */
@DataJpaTest
public class VendorDaoTests extends BaseRepoTest {

	/*
	 * Spring Boot autowired
	 */
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private VendorDao repo;
	
	@Test
	public static void givenNoVendorNameThenFailToSave() {
		
	}
	
}
