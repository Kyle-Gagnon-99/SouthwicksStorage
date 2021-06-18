/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;
import com.southwicksstorage.southwicksstorage.repositories.StandItemDao;

/**
 * @author kyle
 *
 */
@Service
public class StandItemService extends RepositoryService<StandItemEntity>{

	@Autowired
	private StandItemDao repo;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StandItemEntity findById(int id) {
		Optional<StandItemEntity> getStandItem = repo.findById(id);
		
		if(getStandItem.isPresent()) {
			return getStandItem.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(StandItemEntity entity) {
		repo.saveAndFlush(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(StandItemEntity entity) {
		repo.delete(entity);
	}
	
	/**
	 * Find all items associated to the stand
	 * @param entity The stand to find the items by
	 * @return All items belonging to the requested stand
	 */
	public List<StandItemEntity> findAllByStand(StandEntity entity) {
		return repo.findAllByStand(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<StandItemEntity> findAll() {
		return repo.findAll();
	}
	
}
