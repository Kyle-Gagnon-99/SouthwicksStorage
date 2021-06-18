/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.entities.NotificationMessageEntity;
import com.southwicksstorage.southwicksstorage.repositories.NotificationMessageDao;

/**
 * @author kyle
 *
 */
@Service
public class NotificationMessageService extends RepositoryService<NotificationMessageEntity>{

	@Autowired
	private NotificationMessageDao repo;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public NotificationMessageEntity findById(int id) {
		Optional<NotificationMessageEntity> getNoti = repo.findById(id);
		
		if(getNoti.isPresent()) {
			return getNoti.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(NotificationMessageEntity entity) {
		repo.saveAndFlush(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(NotificationMessageEntity entity) {
		repo.delete(entity);
	}
	
	/**
	 * Find the entity by the message
	 * @param message The message to find by
	 * @return The entity otherwise null
	 */
	public NotificationMessageEntity findByMessage(String message) {
		Optional<NotificationMessageEntity> getNotiMessage = repo.findByMessage(message);
		
		if(getNotiMessage.isPresent()) {
			return getNotiMessage.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NotificationMessageEntity> findAll() {
		return repo.findAll();
	}

}
