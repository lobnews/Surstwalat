package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import java.util.Set;

public interface IDispatcherRepository<T, K> {
	public T findById(K id);
	public T save(T entity);
	public Set<T> findAll();
}
