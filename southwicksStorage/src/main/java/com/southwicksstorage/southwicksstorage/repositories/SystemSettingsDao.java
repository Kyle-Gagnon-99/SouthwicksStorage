package com.southwicksstorage.southwicksstorage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.constants.SystemSettingsName;
import com.southwicksstorage.southwicksstorage.entities.SystemSettingsEntity;

@Repository
public interface SystemSettingsDao extends JpaRepository<SystemSettingsEntity, Integer> {
	
	Optional<SystemSettingsEntity> findBySettingsName(SystemSettingsName settingsName);

}
