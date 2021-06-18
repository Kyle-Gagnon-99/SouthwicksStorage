/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.repositories.VendorDao;

/**
 * @author kyle
 *
 */
@Service
public class VendorService extends RepositoryService<VendorEntity>{

	@Autowired
	private VendorDao repo;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VendorEntity findById(int id) {
		Optional<VendorEntity> getVendor = repo.findById(id);
		
		if(getVendor.isPresent()) {
			return getVendor.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(VendorEntity entity) {
		repo.saveAndFlush(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(VendorEntity entity) {
		repo.delete(entity);
	}
	
	/**
	 * Whether or not that vendor exists by the name given
	 * @param name The name of the vendor to find
	 * @return Whether or not that vendor exists by the name given
	 */
	public boolean existsByName(String name) {
		return repo.existsByVendorName(name);
	}
	
	/**
	 * Get the vendor by the given vendor name
	 * @param name The name of the vendor to get
	 * @return If the vendor exists by that name then it will return the vendor otherwise null
	 */
	public VendorEntity findByVendorName(String name) {
		Optional<VendorEntity> getVendor = repo.findByVendorName(name);
		
		if(getVendor.isPresent()) {
			return getVendor.get();
		} else {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<VendorEntity> findAll() {
		return repo.findAll();
	}
	
}
