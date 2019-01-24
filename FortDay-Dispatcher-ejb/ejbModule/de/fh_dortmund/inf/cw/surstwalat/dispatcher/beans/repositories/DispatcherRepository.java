package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.IDispatcherRepository;

/**
 * Abstract repository class for a generic access to the database via EntityManager
 * @author Johannes Heiderich
 */
public abstract class DispatcherRepository<T, K> implements IDispatcherRepository<T, K> {
	
	@PersistenceContext(unitName = "FortDayDB")
	protected EntityManager entityManager;
	
	private Class<T> type;
	
	public DispatcherRepository(Class<T> type) {
		this.type = type;
	}
	
	/**
	 * @see IDispatcherRepository#findById(Object)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public T findById(K id) {
		return entityManager.find(type, id);
	}
	
	/**
	 * @see IDispatcherRepository#save(Object)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public T save(T entity) {
		return entityManager.merge(entity);
	}



//	@SuppressWarnings("unchecked")
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	@Override
//	public Set<T> findAll() {
//	    entityManager.getTransaction().begin();
//	    
//	    String queryString = "SELECT e FROM " + extractTableName() + " e";
//	    List<T> entities = entityManager.createQuery(queryString).getResultList();
//	    entityManager.getTransaction().commit();
//	    if (entities == null) {
//	        return new HashSet<>();
//	    } else {
//	        Set<T> entitySet = new HashSet<>();
//	        for(T e: entities) {
//	        	entitySet.add(e);
//	        }
//	        return entitySet;
//	    }	   
//	}
//	
//	private String extractTableName() {
//		Table table = type.getAnnotation(Table.class);
//		return table != null ? table.name() : type.getSimpleName();
//	}
	
	

}
