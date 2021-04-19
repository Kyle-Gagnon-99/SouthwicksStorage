package com.southwicksstorage.southwicksstorage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;

@Repository
public interface UserDao extends JpaRepository<UserModelEntity, Long>{
	
	public Optional<UserModelEntity> findByUsername(String username);
	public boolean existsByUsername(String username);
	public Optional<UserModelEntity> findById(int id);
	public Optional<UserModelEntity> findByFirstNameAndLastNameAndUsername(String firstName, String lastName, String username);

}
