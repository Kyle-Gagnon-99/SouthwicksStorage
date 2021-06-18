/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.constants.SystemSettingsName;
import com.southwicksstorage.southwicksstorage.entities.SystemSettingsEntity;
import com.southwicksstorage.southwicksstorage.repositories.SystemSettingsDao;

/**
 * @author kyle
 *
 */
@Service
public class SystemSettingsService extends RepositoryService<SystemSettingsEntity>{

	@Autowired
	private SystemSettingsDao repo;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SystemSettingsEntity findById(int id) {
		Optional<SystemSettingsEntity> getSettings = repo.findById(id);
		
		if(getSettings.isPresent()) {
			return getSettings.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(SystemSettingsEntity entity) {
		repo.saveAndFlush(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(SystemSettingsEntity entity) {
		repo.delete(entity);
	}
	
	/**
	 * Get the system settings by the name of the settings provided by the {@link SystemSettingsName} enum
	 * @param settingsName The {@link SystemSettingsName} enum
	 * @return The System Settings Entity if found otherwise null
	 */
	public SystemSettingsEntity findBySettingsName(SystemSettingsName settingsName) {
		Optional<SystemSettingsEntity> getSettings = repo.findBySettingsName(settingsName);
		
		if(getSettings.isPresent()) {
			return getSettings.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SystemSettingsEntity> findAll() {
		return repo.findAll();
	}

}
