/**
 * 
 */
package com.southwicksstorage.southwicksstorage.unit.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

/**
 * @author kyle
 * 
 * Unit test methods in UserDao
 *
 */
@DataJpaTest
public class UserDaoTests {

	/*
	 * Spring Boot autowired
	 */
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserDao repo;
	
	private static Validator validator;
	
	/*
	 * Test Resources
	 */
	private static String testUsernameExceedMax = StringUtils.repeat("A", Constants.TEST_USERNAME_EXCEED_MAX);
	private static String testFNameLNameExceedMax = StringUtils.repeat('A', Constants.TEST_USER_FNAME_LNAME_EXCEED_MAX);
	
	@BeforeAll
	public static void initBeforeAllTests() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	@DisplayName("When no First Name is given then repository fails to save")
	public void save_NoFirstName_ThrowsException() {
		// Given
		UserModelEntity noUsername = new UserModelEntity(null, "User", "testuser", Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		
		//When and Then
		Exception exception = assertThrows(Exception.class, () -> repo.saveAndFlush(noUsername));
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
		Set<ConstraintViolation<UserModelEntity>> expectedConstraintViolations = validator.validate(noUsername);
		String saveConstraintViolationReason = constraintViolations.iterator().next().getMessage();
		String expectedContstraintViolationReason = expectedConstraintViolations.iterator().next().getMessage();
		assertThat(constraintViolations).isEqualTo(expectedConstraintViolations);
		assertThat(saveConstraintViolationReason).isEqualTo(expectedContstraintViolationReason);
		
	}
	
	@Test
	@DisplayName("When First Name exceeds max length is given then repository fails to save")
	public void save_FirstNameExceedsMaxLength_ThrowsException() {
		// Given
		UserModelEntity usernameExceed45Char = new UserModelEntity(testFNameLNameExceedMax, "User", "testuser", Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		
		//When and Then
		Exception exception = assertThrows(Exception.class, () -> repo.saveAndFlush(usernameExceed45Char));
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
		Set<ConstraintViolation<UserModelEntity>> expectedConstraintViolations = validator.validate(usernameExceed45Char);
		String saveConstraintViolationReason = constraintViolations.iterator().next().getMessage();
		String expectedContstraintViolationReason = expectedConstraintViolations.iterator().next().getMessage();
		assertThat(constraintViolations).isEqualTo(expectedConstraintViolations);
		assertThat(saveConstraintViolationReason).isEqualTo(expectedContstraintViolationReason);
		
	}
	
	@Test
	@DisplayName("When no Last Name is given then repository fails to save")
	public void save_NoLastName_ThrowsException() {
		// Given
		UserModelEntity noUsername = new UserModelEntity("Test", null, "testuser", Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		
		//When and Then
		Exception exception = assertThrows(Exception.class, () -> repo.saveAndFlush(noUsername));
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
		Set<ConstraintViolation<UserModelEntity>> expectedConstraintViolations = validator.validate(noUsername);
		String saveConstraintViolationReason = constraintViolations.iterator().next().getMessage();
		String expectedContstraintViolationReason = expectedConstraintViolations.iterator().next().getMessage();
		assertThat(constraintViolations).isEqualTo(expectedConstraintViolations);
		assertThat(saveConstraintViolationReason).isEqualTo(expectedContstraintViolationReason);
		
	}
	
	@Test
	@DisplayName("When Last Name exceeds max length is given then repository fails to save")
	public void save_LastNameExceedsMaxLength_ThrowsException() {
		// Given
		UserModelEntity usernameExceed45Char = new UserModelEntity("Test", testFNameLNameExceedMax, "testuser", Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		
		//When and Then
		Exception exception = assertThrows(Exception.class, () -> repo.saveAndFlush(usernameExceed45Char));
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
		Set<ConstraintViolation<UserModelEntity>> expectedConstraintViolations = validator.validate(usernameExceed45Char);
		String saveConstraintViolationReason = constraintViolations.iterator().next().getMessage();
		String expectedContstraintViolationReason = expectedConstraintViolations.iterator().next().getMessage();
		assertThat(constraintViolations).isEqualTo(expectedConstraintViolations);
		assertThat(saveConstraintViolationReason).isEqualTo(expectedContstraintViolationReason);
		
	}
	
	@Test
	@DisplayName("When no Username is given then repository fails to save")
	public void save_NoUsername_ThrowsException() {
		// Given
		UserModelEntity noUsername = new UserModelEntity("Test", "User", null, Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		
		//When and Then
		Exception exception = assertThrows(Exception.class, () -> repo.saveAndFlush(noUsername));
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
		Set<ConstraintViolation<UserModelEntity>> expectedConstraintViolations = validator.validate(noUsername);
		String saveConstraintViolationReason = constraintViolations.iterator().next().getMessage();
		String expectedContstraintViolationReason = expectedConstraintViolations.iterator().next().getMessage();
		assertThat(constraintViolations).isEqualTo(expectedConstraintViolations);
		assertThat(saveConstraintViolationReason).isEqualTo(expectedContstraintViolationReason);
		
	}
	
	@Test
	@DisplayName("When Username exceeds max length is given then repository fails to save")
	public void save_UsernameExceedsMaxLength_ThrowsException() {
		// Given
		UserModelEntity usernameExceed45Char = new UserModelEntity("Test", "User", testUsernameExceedMax, Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		
		//When and Then
		Exception exception = assertThrows(Exception.class, () -> repo.saveAndFlush(usernameExceed45Char));
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
		Set<ConstraintViolation<UserModelEntity>> expectedConstraintViolations = validator.validate(usernameExceed45Char);
		String saveConstraintViolationReason = constraintViolations.iterator().next().getMessage();
		String expectedContstraintViolationReason = expectedConstraintViolations.iterator().next().getMessage();
		assertThat(constraintViolations).isEqualTo(expectedConstraintViolations);
		assertThat(saveConstraintViolationReason).isEqualTo(expectedContstraintViolationReason);
		
	}
	
	@Test
	@DisplayName("When no Password is given then repository fails to save")
	public void save_NoPassword_ThrowsException() {
		// Given
		UserModelEntity noPassword = new UserModelEntity("Test", "User", "testuser", null, Roles.MANAGER, null);
		
		//When and Then
		Exception exception = assertThrows(Exception.class, () -> repo.saveAndFlush(noPassword));
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
		Set<ConstraintViolation<UserModelEntity>> expectedConstraintViolations = validator.validate(noPassword);
		String saveConstraintViolationReason = constraintViolations.iterator().next().getMessage();
		String expectedContstraintViolationReason = expectedConstraintViolations.iterator().next().getMessage();
		assertThat(constraintViolations).isEqualTo(expectedConstraintViolations);
		assertThat(saveConstraintViolationReason).isEqualTo(expectedContstraintViolationReason);
		
	}
	
	@Test
	@DisplayName("When no Role is given then repository fails to save")
	public void save_NoRole_ThrowsException() {
		// Given
		UserModelEntity noUsername = new UserModelEntity("Test", "User", "testuser", Constants.DEFAULT_PASSWORD, null, null);
		
		//When and Then
		Exception exception = assertThrows(Exception.class, () -> repo.saveAndFlush(noUsername));
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
		Set<ConstraintViolation<UserModelEntity>> expectedConstraintViolations = validator.validate(noUsername);
		String saveConstraintViolationReason = constraintViolations.iterator().next().getMessage();
		String expectedContstraintViolationReason = expectedConstraintViolations.iterator().next().getMessage();
		assertThat(constraintViolations).isEqualTo(expectedConstraintViolations);
		assertThat(saveConstraintViolationReason).isEqualTo(expectedContstraintViolationReason);
		
	}
	
	@Test
	@DisplayName("When a valid username is given then find in repository")
	public void findByUsername_UsernameValid_OptionalPresentWithUser() {
		// Given
		UserModelEntity newUserManager = new UserModelEntity("Test", "Manager", "testmanager", Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		UserModelEntity newUserTeamMember = new UserModelEntity("Test", "Team Member", "testteammember", Constants.DEFAULT_PASSWORD, Roles.TEAM_MEMBER, null);
		entityManager.persist(newUserManager);
		entityManager.persist(newUserTeamMember);
		
		// When
		Optional<UserModelEntity> foundUserByUsername = repo.findByUsername(newUserManager.getUsername());
		Optional<UserModelEntity> foundTMByUsername = repo.findByUsername(newUserTeamMember.getUsername());
		
		// Then
		assertThat(foundUserByUsername.isPresent()).isEqualTo(true);
		assertThat(foundTMByUsername.isPresent()).isEqualTo(true);
		assertThat(foundUserByUsername.get().getUsername()).isEqualTo(newUserManager.getUsername());
		assertThat(foundTMByUsername.get().getUsername()).isEqualTo(newUserTeamMember.getUsername());
	}
	
	@Test
	@DisplayName("When an invalid username is given then do not find in repository")
	public void findByUsername_UsernameInvalid_OptionalEmpty() {
		// Give - When
		Optional<UserModelEntity> didNotFindByUsername = repo.findByUsername("invalid");
		
		// Then
		assertThat(didNotFindByUsername.isEmpty()).isEqualTo(true);
	}
	
	@Test
	@DisplayName("When a valid username exists is given then return true")
	public void existsByUsername_ValidUsername_True() {
		// Given
		UserModelEntity newUserManager = new UserModelEntity("Test", "Manager", "testmanager", Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		entityManager.persist(newUserManager);
		
		// When
		boolean existsByUsername = repo.existsByUsername(newUserManager.getUsername());
		
		// Then
		assertThat(existsByUsername).isEqualTo(true);
	}
	
	@Test
	@DisplayName("When an invalid username exists is given then return false")
	public void existsByUsername_InvalidUsername_False() {
		// Given - When
		boolean doesNotExistByUsername = repo.existsByUsername("invalid");
		
		// Then
		assertThat(doesNotExistByUsername).isEqualTo(false);
	}
	
	@Test
	@DisplayName("When a valid id is given then find in repository")
	public void findById_ValidId_OptionalPresentWithUser() {
		// Given
		UserModelEntity newUserManager = new UserModelEntity("Test", "Manager", "testmanager", Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		entityManager.persist(newUserManager);
		
		// When
		Optional<UserModelEntity> findById = repo.findById(newUserManager.getId());
		
		// Then
		assertThat(findById.isPresent()).isEqualTo(true);
		assertThat(findById.get().getId()).isEqualTo(newUserManager.getId());
	}
	
	@Test
	@DisplayName("When an invalid id is given then do not find in repository")
	public void findById_InvalidId_OptionalEmpty() {
		// Given - When
		Optional<UserModelEntity> findById = repo.findById(100);
		
		// Then
		assertThat(findById.isEmpty()).isEqualTo(true);
	}
	
	@Test
	@DisplayName("When a valid role is given then find all users with that role")
	public void findAllByRole_ValidRole_FindAllUsersWithRole() {
		// Given
		UserModelEntity newUserManager = new UserModelEntity("Test", "Manager", "testmanager", Constants.DEFAULT_PASSWORD, Roles.MANAGER, null);
		UserModelEntity newUserTeamMember = new UserModelEntity("Test", "Team Member", "testteammember", Constants.DEFAULT_PASSWORD, Roles.TEAM_MEMBER, null);
		entityManager.persist(newUserManager);
		entityManager.persist(newUserTeamMember);
		
		// When
		List<UserModelEntity> findUsersByManager = repo.findAllByRole(Roles.MANAGER);
		List<UserModelEntity> findUsersByTeamMember = repo.findAllByRole(Roles.TEAM_MEMBER);
		
		assertThat(findUsersByManager.size()).isEqualTo(1);
		assertThat(findUsersByTeamMember.size()).isEqualTo(1);
		assertThat(findUsersByManager.get(0)).isEqualTo(newUserManager);
		assertThat(findUsersByTeamMember.get(0)).isEqualTo(newUserTeamMember);
	}
	
}
