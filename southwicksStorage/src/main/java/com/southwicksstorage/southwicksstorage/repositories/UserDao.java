package com.southwicksstorage.southwicksstorage.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.entities.UserModelEntity;

@Repository
public interface UserDao extends JpaRepository<UserModelEntity, Integer>{
	
	public Optional<UserModelEntity> findByUsername(String username);
	public boolean existsByUsername(String username);
	public List<UserModelEntity> findAllByRole(Roles role);

}
