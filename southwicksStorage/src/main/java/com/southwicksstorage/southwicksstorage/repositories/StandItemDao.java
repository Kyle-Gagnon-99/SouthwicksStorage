package com.southwicksstorage.southwicksstorage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.StandItemEntity;

@Repository
public interface StandItemDao extends JpaRepository<StandItemEntity, Long> {

}
