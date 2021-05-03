package com.southwicksstorage.southwicksstorage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;

@Repository
public interface TypeOfStorageDao extends JpaRepository<TypeOfStorageEntity, Long> {

}
