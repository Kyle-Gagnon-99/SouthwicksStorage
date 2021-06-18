package com.southwicksstorage.southwicksstorage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.StandEntity;

@Repository
public interface StandDao extends JpaRepository<StandEntity, Integer> {
	
	boolean existsByName(String name);
	Optional<StandEntity> findByName(String name);

}
