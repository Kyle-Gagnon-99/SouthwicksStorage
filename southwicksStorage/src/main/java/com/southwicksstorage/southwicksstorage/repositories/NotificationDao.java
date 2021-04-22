package com.southwicksstorage.southwicksstorage.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.constants.NotificationMessages;
import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;

@Repository
public interface NotificationDao extends JpaRepository<NotificationModelEntity, Long>{
	
	Optional<NotificationModelEntity> findByMessageAndUserModel(NotificationMessages message, UserModelEntity userModel);
	List<NotificationModelEntity> findAllByUserModel(UserModelEntity userModel);
	List<NotificationModelEntity> findAllByUserModelAndIsRead(UserModelEntity userModel, boolean isRead);
	Optional<NotificationModelEntity> findById(int id);

}