/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.repositories.StandDao;

/**
 * @author kyle
 *
 */
@Service
public class StandService extends RepositoryService<StandEntity>{

	@Autowired
	private StandDao repo;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StandEntity findById(int id) {
		Optional<StandEntity> getStand = repo.findById(id);
		
		if(getStand.isPresent()) {
			return getStand.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(StandEntity entity) {
		repo.saveAndFlush(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(StandEntity entity) {
		repo.delete(entity);
	}
	
	/**
	 * Check to see if there is a stand that exists by the name given
	 * @param name The name to check
	 * @return Whether or not the stand exists by that name
	 */
	public boolean existsByName(String name) {
		return repo.existsByName(name);
	}
	
	/**
	 * Return a stand entity by the name of the stand
	 * @param name The name of the stand to return
	 * @return The stand entity if it exists otherwise return null
	 */
	public StandEntity findByName(String name) {
		Optional<StandEntity> getStand = repo.findByName(name);
		
		if(getStand.isPresent()) {
			return getStand.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<StandEntity> findAll() {
		return repo.findAll();
	}
	
}
