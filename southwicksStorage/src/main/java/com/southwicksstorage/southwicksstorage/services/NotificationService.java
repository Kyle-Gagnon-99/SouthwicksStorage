/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.entities.NotificationMessageEntity;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;
import com.southwicksstorage.southwicksstorage.repositories.NotificationDao;

/**
 * @author kyle
 *
 */
@Service
public class NotificationService extends RepositoryService<NotificationModelEntity> {

	@Autowired
	private NotificationDao repo;
	
	/**
	 * Find a notification entity by the message (entity) and the user (entity)
	 * @param message The Notification Message Entity
	 * @param user The User Entity
	 * @return The Notification if found or null if not
	 */
	public NotificationModelEntity findByMessageAndUser(NotificationMessageEntity message, UserModelEntity user) {	
		Optional<NotificationModelEntity> getNoti = repo.findByMessageAndUserModel(message, user);
		
		if(getNoti.isPresent()) {
			return getNoti.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Find all notifications by user (entity)
	 * @param user The User Entity
	 * @return The list of Notifications
	 */
	public List<NotificationModelEntity> findNotisByUser(UserModelEntity user) {
		return repo.findAllByUserModel(user);
	}
	
	/**
	 * Find all notifications that has not been read by the logged in user
	 * @param user The User (entity)
	 * @param isRead Whether or not the notification has been read
	 * @return A list of notifications for that user that is or is not read
	 */
	public List<NotificationModelEntity> findNotisByUserandIsRead(UserModelEntity user, boolean isRead) {
		return repo.findAllByUserModelAndIsRead(user, isRead);
	}
	
	/**
	 * Find the notification by id (primary key)
	 * @param id The id of the notification
	 * @return The notification entity if it exists. If not then null
	 */
	@Override
	public NotificationModelEntity findById(int id) {
		Optional<NotificationModelEntity> getNoti = repo.findById(id);
		
		if(getNoti.isPresent()) {
			return getNoti.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Find all notifications that is read and is visible for that user
	 * @param user The User (entity)
	 * @param isRead Whether or not the notification has been read
	 * @param isVisible Whether or not the notification is seen
	 * @return
	 */
	public List<NotificationModelEntity> findNotisByUserIsReadAndIsVisible(UserModelEntity user, boolean isRead, boolean isVisible) {
		return repo.findAllByUserModelAndIsReadAndIsVisible(user, isRead, isVisible);
	}
	
	/**
	 * Save the passed notification entity to the database
	 * @param noti The notification to save
	 */
	@Override
	public void save(NotificationModelEntity noti) {
		repo.saveAndFlush(noti);
	}
	
	/**
	 * Delete the passed notification entity to the database
	 * @param noti
	 */
	@Override
	public void delete(NotificationModelEntity noti) {
		repo.delete(noti);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NotificationModelEntity> findAll() {
		return repo.findAll();
	}
	
	
}
