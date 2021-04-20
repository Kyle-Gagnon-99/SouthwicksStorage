package com.southwicksstorage.southwicksstorage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.NotificationModelEntity;

@Repository
public interface NotificationDao extends JpaRepository<NotificationModelEntity, Long>{
	

}