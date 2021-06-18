/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.repositories.TypeOfStorageDao;

/**
 * @author kyle
 *
 */
@Service
public class TypeOfStorageService extends RepositoryService<TypeOfStorageEntity>{

	@Autowired
	private TypeOfStorageDao repo;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeOfStorageEntity findById(int id) {
		Optional<TypeOfStorageEntity> getTOS = repo.findById(id);
		
		if(getTOS.isPresent()) {
			return getTOS.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(TypeOfStorageEntity entity) {
		repo.saveAndFlush(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(TypeOfStorageEntity entity) {
		repo.delete(entity);
	}
	
	/**
	 * Check to see if the type of storage already exists by name
	 * @param name The name of the type of storage to look for
	 * @return Whether or not that type of storage exists
	 */
	public boolean existsByName(String name) {
		return repo.existsByName(name);
	}
	
	/**
	 * Get the TOS Entity by the name
	 * @param name The name of the TOS to get
	 * @return The TOS Entity if found otherwise null
	 */
	public TypeOfStorageEntity findByName(String name) {
		Optional<TypeOfStorageEntity> getTOS = repo.findByName(name);
		
		if(getTOS.isPresent()) {
			return getTOS.get();
		} else {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TypeOfStorageEntity> findAll() {
		return repo.findAll();
	}
	
}
