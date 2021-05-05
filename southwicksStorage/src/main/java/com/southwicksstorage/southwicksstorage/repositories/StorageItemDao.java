/**
 * 
 */
package com.southwicksstorage.southwicksstorage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;

/**
 * @author kyle
 *
 */
@Repository
public interface StorageItemDao extends JpaRepository<StorageItemEntity, Long> {
	
	public Optional<StorageItemEntity> findById(int id);
	public boolean existsByNameAndVendor(String name, VendorEntity vendor);
	public boolean existsByName(String name);

}
