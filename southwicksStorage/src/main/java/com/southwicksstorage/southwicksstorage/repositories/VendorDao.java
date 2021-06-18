/**
 * 
 */
package com.southwicksstorage.southwicksstorage.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.southwicksstorage.southwicksstorage.entities.VendorEntity;

/**
 * @author kyle
 *
 */
@Repository
public interface VendorDao extends JpaRepository<VendorEntity, Integer> {

	public boolean existsByVendorName(String vendorName);
	public Optional<VendorEntity> findByVendorName(String name);
}
