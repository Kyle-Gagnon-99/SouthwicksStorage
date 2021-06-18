package com.southwicksstorage.southwicksstorage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.StandEntity;
import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;

@Repository
public interface StandItemDao extends JpaRepository<StandItemEntity, Integer> {
	
	public List<StandItemEntity> findAllByStand(StandEntity stand);

}
