package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.IDispatcherRepository;

/**
 * Session Bean implementation class DispatcherRepository
 * @author Johannes Heiderich
 */
public abstract class DispatcherRepository<T, K> implements IDispatcherRepository<T, K> {
	
//	@PersistenceContext(unitName = "ChatDB")
//	protected EntityManager entityManager;
	
	private Class<T> type;
	
	public DispatcherRepository(Class<T> type) {
		this.type = type;
	}
	
	@Override
	public T findById(K id) {
		// Mocked Method
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
	
	@Override
	public T save(T entity) {
		// Mocked Method
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}

}
