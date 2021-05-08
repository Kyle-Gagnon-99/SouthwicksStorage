/**
 * 
 */
package com.southwicksstorage.southwicksstorage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.OrderReportEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;

/**
 * @author kyle
 *
 */
@Repository
public interface OrderReportDao extends JpaRepository<OrderReportEntity, Integer> {
	
	public boolean existsByStorageItem(StorageItemEntity storageItem);
	public Optional<OrderReportEntity> findByStorageItem(StorageItemEntity storageItem);

}
