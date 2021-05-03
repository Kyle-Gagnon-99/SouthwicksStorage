/**
 * 
 */
package com.southwicksstorage.southwicksstorage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;

/**
 * @author kyle
 *
 */
@Repository
public interface StorageItemDao extends JpaRepository<StorageItemEntity, Long> {

}
