/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.constants.StorageType;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.entities.TypeOfStorageEntity;
import com.southwicksstorage.southwicksstorage.entities.VendorEntity;
import com.southwicksstorage.southwicksstorage.repositories.StorageItemDao;

/**
 * @author kyle
 *
 */
@Service
public class StorageItemService extends RepositoryService<StorageItemEntity>{

	@Autowired
	private StorageItemDao repo;
	
	@Autowired
	private VendorService vendorService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StorageItemEntity findById(int id) {
		Optional<StorageItemEntity> getStorageItem = repo.findById(id);
		
		if(getStorageItem.isPresent()) {
			return getStorageItem.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(StorageItemEntity entity) {
		repo.saveAndFlush(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(StorageItemEntity entity) {
		repo.delete(entity);
	}
	
	/**
	 * Check if a storage item exists by the vendor entity that belongs to a vendor
	 * @param name The name of the storage item
	 * @param vendor The vendor entity to check if storage item belongs to that
	 * @return Whether or not the item by that name belongs to that vendor
	 */
	public boolean existsByNameAndVendor(String name, VendorEntity vendor) {
		return repo.existsByNameAndVendor(name, vendor);
	}
	
	/**
	 * Check if a storage item exists by the id that belongs to the vendor
	 * @param name The name of the storage item
	 * @param vendor The vendor id to check if storage item belongs to that
	 * @return Whether or not the item by that name belongs to that vendor
	 */
	public boolean existsByNameAndVendorId(String name, int vendorId) {
		return repo.existsByNameAndVendor(name, vendorService.findById(vendorId));
	}
	
	/**
	 * Check if a storage item exists by the name that belongs to a vendor
	 * @param name The name of the storage item
	 * @param vendor The vendor name to check if storage item belongs to that
	 * @return Whether or not the item by that name belongs to that vendor
	 */
	public boolean existsByNameAndVendorName(String name, String vendorName) {
		return repo.existsByNameAndVendor(name, vendorService.findByVendorName(vendorName));
	}
	
	/**
	 * Get the storage item by the name and vendor entity given
	 * @param name The name of the storage item
	 * @param vendor The vendor entity to check if storage item belongs to that
	 * @return The storage item entity if it exists otherwise null
	 */
	public StorageItemEntity findByNameAndVendor(String name, VendorEntity vendor) {
		Optional<StorageItemEntity> getStorageItem = repo.findByNameAndVendor(name, vendor);
		
		if(getStorageItem.isPresent()) {
			return getStorageItem.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Get the storage item by the name and the vendor id given
	 * @param name The name of the storage item
	 * @param vendor The vendor id to check if storage item belongs to that
	 * @return The storage item entity if it exists otherwise null
	 */
	public StorageItemEntity findByNameAndVendorId(String name, int vendorId) {
		Optional<StorageItemEntity> getStorageItem = repo.findByNameAndVendor(name, vendorService.findById(vendorId));
		
		if(getStorageItem.isPresent()) {
			return getStorageItem.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Get the storage item by the name and the vendor name given
	 * @param name The name of the storage item
	 * @param vendor The vendor name to check if storage item belongs to that
	 * @return The storage item entity if it exists otherwise null
	 */
	public StorageItemEntity findByNameAndVendorName(String name, String vendorName) {
		Optional<StorageItemEntity> getStorageItem = repo.findByNameAndVendor(name, vendorService.findByVendorName(vendorName));
		
		if(getStorageItem.isPresent()) {
			return getStorageItem.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Check if the storage item exists by name
	 * @param name The name of the storage item to check
	 * @return Whether or not the storage item exists by that name
	 */
	public boolean existsByName(String name) {
		return repo.existsByName(name);
	}
	
	/**
	 * Find all storage items that are stored by {@link StorageType}
	 * @param storedType The {@link StorageType}
	 * @return The list of all items that are stored by the storage type
	 */
	public List<StorageItemEntity> findAllByStorageType(StorageType storedType) {
		return repo.findAllByStoredType(storedType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<StorageItemEntity> findAll() {
		return repo.findAll();
	}
	
	/**
	 * Find the item by just the name in the storage
	 * @param name Find by the name
	 * @return The entity to return
	 */
	public StorageItemEntity findByName(String name) {
		Optional<StorageItemEntity> findItemByName = repo.findByName(name);
		
		if(findItemByName.isPresent()) {
			return findItemByName.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Find all items by the type of storage
	 * @param typeOfStorage The type of storage to find by
	 * @return All items by that type of storage
	 */
	public List<StorageItemEntity> findAllByTypeOfStorage(TypeOfStorageEntity typeOfStorage) {
		return repo.findAllByTypeOfStorage(typeOfStorage);
	}
	
}
