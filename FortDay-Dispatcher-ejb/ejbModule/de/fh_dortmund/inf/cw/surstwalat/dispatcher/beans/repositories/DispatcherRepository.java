package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.IDispatcherRepository;

/**
 * Session Bean implementation class DispatcherRepository
 * @author Johannes Heiderich
 */
public abstract class DispatcherRepository<T, K> implements IDispatcherRepository<T, K> {
	
	@PersistenceContext(unitName = "FortDayDB")
	protected EntityManager entityManager;
	
	private Class<T> type;
	
	public DispatcherRepository(Class<T> type) {
		this.type = type;
	}
	
	
	
	@Override
	public T findById(K id) {
		return entityManager.find(type, id);
	}
	
	@Override
	public T save(T entity) {
		return entityManager.merge(entity);
	}



	@Override
	public Set<T> findAll() {
		return null;
	}
	
	

}
