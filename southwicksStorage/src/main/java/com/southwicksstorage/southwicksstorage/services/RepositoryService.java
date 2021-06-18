/**
 * 
 */
package com.southwicksstorage.southwicksstorage.services;

import java.util.List;

/**
 * Class to be used by all repository service layers. Provides basic methods
 * @author kyle
 *
 */
public abstract class RepositoryService<E> {
	
	/**
	 * Find the Entity by the id (primary key)
	 * @param id The id to find by
	 * @return Entity is returned if found if not then null
	 */
	public abstract E findById(int id);
	
	/**
	 * Save entity to the database
	 * @param entity Entity to save
	 */
	public abstract void save(E entity);
	
	/**
	 * Delete the entity from the database
	 * @param entity Entity to delete
	 */
	public abstract void delete(E entity);
	
	/**
	 * Get a list of all entities from the database
	 * @return A java list of all entities
	 */
	public abstract List<E> findAll();

}
