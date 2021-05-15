package com.southwicksstorage.southwicksstorage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.NotificationMessageEntity;

@Repository
public interface NotificationMessageDao extends JpaRepository<NotificationMessageEntity, Integer> {
	
	public Optional<NotificationMessageEntity> findByMessage(String message);

}
