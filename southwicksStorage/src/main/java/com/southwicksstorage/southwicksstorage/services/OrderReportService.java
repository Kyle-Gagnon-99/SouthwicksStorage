/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southwicksstorage.southwicksstorage.entities.OrderReportEntity;
import com.southwicksstorage.southwicksstorage.entities.StorageItemEntity;
import com.southwicksstorage.southwicksstorage.repositories.OrderReportDao;

/**
 * Service layer for the Order Report Repository
 * @author kyle
 *
 */
@Service
public class OrderReportService extends RepositoryService<OrderReportEntity>{

	@Autowired
	private OrderReportDao repo;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderReportEntity findById(int id) {
		Optional<OrderReportEntity> getOrderReport = repo.findById(id);
		
		if(getOrderReport.isPresent()) {
			return getOrderReport.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(OrderReportEntity entity) {
		repo.saveAndFlush(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(OrderReportEntity entity) {
		repo.delete(entity);
	}
	
	/**
	 * Check if the storage item is already in the report
	 * @param entity The storage item to find
	 * @return Returns whether or not the storage item is in the report or not
	 */
	public boolean existsByStorageItem(StorageItemEntity entity) {
		return repo.existsByStorageItem(entity);
	}
	
	/**
	 * Finds the order entity by the storage item
	 * @param entity The storage item to get report for
	 * @return Returns the order entity if there is a storage item that exists otherwise returns null
	 */
	public OrderReportEntity findByStorageItem(StorageItemEntity entity) {
		Optional<OrderReportEntity> getOrderReport = repo.findByStorageItem(entity);
		
		if(getOrderReport.isPresent()) {
			return getOrderReport.get();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<OrderReportEntity> findAll() {
		return repo.findAll();
	}
	
	
	
}
