package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import java.util.Set;
/**
 * 
 * @author Johannes Heiderich
 *
 */
public interface IDispatcherRepository<T, K> {
	/**
	 * Selects an entity with the given id from the database
	 * @param id the id object
	 * @return object of entity type
	 */
	public T findById(K id);
	/**
	 * Saves an entity to the database
	 * @param entity the entity to save
	 * @return the saved entity object
	 */
	public T save(T entity);
//	public Set<T> findAll();
}
