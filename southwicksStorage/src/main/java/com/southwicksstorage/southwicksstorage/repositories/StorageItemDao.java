/**
 * 
 */
package com.southwicksstorage.southwicksstorage.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;

/**
 * @author kyle
 *
 */
@Repository
public interface StorageItemDao extends JpaRepository<StorageItemEntity, Integer> {
	
	public boolean existsByNameAndVendor(String name, VendorEntity vendor);
	public boolean existsByName(String name);
	public Optional<StorageItemEntity> findByNameAndVendor(String name, VendorEntity vendor);
	public List<StorageItemEntity> findAllByStoredType(StorageType storedType);
	public Optional<StorageItemEntity> findByName(String name);
	public List<StorageItemEntity> findAllByTypeOfStorage(TypeOfStorageEntity typeOfStorage);

}
