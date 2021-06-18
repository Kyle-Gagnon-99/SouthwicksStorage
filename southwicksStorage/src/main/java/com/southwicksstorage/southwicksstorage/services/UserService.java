/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.repositories.UserDao;

/**
 * @author kyle
 *
 */
@Service
public class UserService extends RepositoryService<UserModelEntity> {

	@Autowired
	private UserDao repo;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserModelEntity findById(int id) {
		Optional<UserModelEntity> getUser = repo.findById(id);
		
		if(getUser.isPresent()) {
			return getUser.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(UserModelEntity entity) {
		repo.saveAndFlush(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(UserModelEntity entity) {
		repo.delete(entity);
	}
	
	/**
	 * Get the User Entity by the username
	 * @param username The username to get the entity by
	 * @return If found in the database then the user is returned otherwise null
	 */
	public UserModelEntity findByUsername(String username) {
		Optional<UserModelEntity> getUser = repo.findByUsername(username);
		
		if(getUser.isPresent()) {
			return getUser.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Whether or not the given username exists in the database
	 * @param username The username to search for
	 * @return Whether or not a user exists by the username
	 */
	public boolean existsByUsername(String username) {
		return repo.existsByUsername(username);
	}
	
	/**
	 * A list of all user entities that belong to the given {@link Roles} enum
	 * @param role The {@link Roles} enum
	 * @return The list of all user entities
	 */
	public List<UserModelEntity> findAllByRole(Roles role) {
		return repo.findAllByRole(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserModelEntity> findAll() {
		return repo.findAll();
	}
}
