/**
 * 
 */
package com.southwicksstorage.southwicksstorage.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;
import com.southwicksstorage.southwicksstorage.services.UserService;
import com.southwicksstorage.southwicksstorage.unit.BaseUnitTest;
import com.southwicksstorage.southwicksstorage.unit.configuration.UnitTestConstants;

/**
 * @author Kyle Gagnon
 * Created On: 2021-06-15
 *
 */
public class UserServiceTests extends BaseUnitTest {
	
	@InjectMocks
	private UserService userService;
	
	@MockBean
	private UserDao repo;
	
	private static UserModelEntity testUserObject = UnitTestConstants.USER_MANAGER_OBJECT;
	private static UserModelEntity testTMObject = UnitTestConstants.USER_TEAM_MEMBER_OBJECT;
	private static Optional<UserModelEntity> getTestUser = Optional.of(testUserObject);
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	/**
	 * Method: findByUsername
	 * Given: A valid username to find
	 * Result: UserModelEntity
	 * When a valid username is given then test that a UserModelEntity is the result
	 */
	@Test
	public void givenAValidUsernameThenFindUserAndReturnUserForFindByUsername() {
		// Successfully get a user
		when(repo.findByUsername(testUserObject.getUsername())).thenReturn(getTestUser);
		UserModelEntity userFound = userService.findByUsername(UnitTestConstants.TEST_USERNAME_MANAGER);
		
		assertThat(userFound).isNotEqualTo(null);
	}
	
	/**
	 * 
	 * Method Being Tested: findByUsername
	 * Given: An invalid username to find
	 * Result: {@code null}
	 * Info: When an invalidusername is given then test that null is the result
	 */
	@Test
	public void givenAnInvalidUsernameThenReturnNullForFindByUsername() {
		Mockito.when(repo.findByUsername("Invalid")).thenReturn(Optional.empty());
		UserModelEntity userNotFound = userService.findByUsername("Invalid");
		
		assertThat(userNotFound).isEqualTo(null);
	}
	
	/**
	 * 
	 * Method Being Tested: save 
	 * Given: A valid user object {@code UserModelEntity}
	 * Result: {@code void}
	 * Info: When a valid user object is given verify that saveAndFlush is called once
	 */
	@Test
	public void givenAValidTestUserThenSaveCalledSuccesfullyForSave() {
		// Verify that save in the repo is called
		userService.save(testUserObject);
		
		Mockito.verify(repo, times(1)).saveAndFlush(testUserObject);
	}
	
	/**
	 * 
	 * Method Being Tested: delete
	 * Given: A valid user object {@code UserModelEntity}
	 * Result: {@code null}
	 * Info: When a valid user object is given verify that delete is called once
	 */
	@Test
	public void givenAValidTestUserThenDeleteCalledSuccessfullyForSave() {
		// Verify that delete in the repo is called
		userService.delete(testUserObject);
		
		Mockito.verify(repo, times(1)).delete(testUserObject);
	}
	
	/**
	 * 
	 * Method Being Tested: findById 
	 * Given: A valid id {@code int}
	 * Result: {@code UserModelEntity}
	 * Info: When a valid id is given then a user should be returned
	 */
	@Test
	public void givenAValidIdThenReturnUserModelEntityForFindById() {
		Mockito.when(repo.findById(testUserObject.getId())).thenReturn(getTestUser);
		
		UserModelEntity getUser = userService.findById(testUserObject.getId());
		
		assertThat(getUser).isEqualTo(testUserObject);
		assertThat(getUser).isNotEqualTo(null);
	}
	
	/**
	 * 
	 * Method Being Tested: findById
	 * Given: An invalid id
	 * Result: {@code null}
	 * Info: When an invalid id then null should be returned
	 */
	@Test
	public void givenAnInvalidIdThenReturnNullForFindById() {
		int invalidId = 100;
		Mockito.when(repo.findById(invalidId)).thenReturn(Optional.empty());
		
		UserModelEntity getUser = userService.findById(invalidId);
		
		assertThat(getUser).isEqualTo(null);
	}
	
	/**
	 * 
	 * Method Being Tested: existsByUsername 
	 * Given: A valid username
	 * Result: {@code true}
	 * Info: When a valid username is given then return true
	 */
	@Test
	public void givenAValidUsernameThenReturnTrueForExistsByUsername() {
		Mockito.when(repo.existsByUsername(testUserObject.getUsername())).thenReturn(true);
		
		boolean existsByUsername = userService.existsByUsername(UnitTestConstants.TEST_USERNAME_MANAGER);
		
		assertThat(existsByUsername).isEqualTo(true);
	}
	
	/**
	 * 
	 * Method Being Tested: existsByUsername 
	 * Given: An invalid username
	 * Result: {@code false}
	 * Info: When an invalid username is given then return false
	 */
	@Test
	public void givenAnInvalidUsernameThenReturnFalseForExistsByUsername() {
		String invalidUsername = "Invalid";
		Mockito.when(repo.existsByUsername(invalidUsername)).thenReturn(false);
		
		boolean existsByUsername = userService.existsByUsername(invalidUsername);
		
		assertThat(existsByUsername).isEqualTo(false);
	}
	
	/**
	 * 
	 * Method Being Tested: findAllByRole 
	 * Given: At least one valid user with a manager role
	 * Result: A list containing all valid users with manager roles
	 * Info: When a valid user with a manager role is given then return the list that contains that valid user
	 */
	@Test
	public void givenAValidManagerThenReturnListOfManagerForFindAllByRole() {
		List<UserModelEntity> returnMockList = Arrays.asList(testUserObject);
		Mockito.when(repo.findAllByRole(Roles.MANAGER)).thenReturn(returnMockList);
		
		List<UserModelEntity> resultList = userService.findAllByRole(Roles.MANAGER);
		
		assertThat(resultList).isEqualTo(returnMockList);
	}
	
	/**
	 * 
	 * Method Being Tested: findAllByRole 
	 * Given: At least one valid user with a team member role
	 * Result: A list containing all valid users with team member roles
	 * Info: When a valid user with a team emmber role is given then return the list that contains that valid user
	 */
	@Test
	public void givenAValidTeamMemberThenReturnListOfTeamMemberForFindAllByRole() {
		List<UserModelEntity> returnMockList = Arrays.asList(testTMObject);
		Mockito.when(repo.findAllByRole(Roles.TEAM_MEMBER)).thenReturn(returnMockList);
		
		List<UserModelEntity> resultList = userService.findAllByRole(Roles.TEAM_MEMBER);
		
		assertThat(resultList).isEqualTo(returnMockList);
	}
	
	/**
	 * 
	 * Method Being Tested: findAll 
	 * Given: A list of users
	 * Result: A list of all users
	 * Info: Verify that when findAll is called that findAll from the repository side is called
	 */
	@Test
	public void givenAListOfUsersThenReturnTheSameListOfUsersForFindAll() {
		List<UserModelEntity> returnMockList = Arrays.asList(testTMObject, testUserObject);
		Mockito.when(repo.findAll()).thenReturn(returnMockList);
		
		List<UserModelEntity> resultList = userService.findAll();
		
		assertThat(resultList).isEqualTo(returnMockList);
		assertThat(resultList.size()).isEqualTo(2);
	}
	
	

}
