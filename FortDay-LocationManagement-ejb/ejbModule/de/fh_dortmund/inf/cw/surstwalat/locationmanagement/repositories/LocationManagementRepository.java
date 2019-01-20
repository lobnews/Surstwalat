package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.repositories;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public abstract class LocationManagementRepository<T, K>
{

    @PersistenceContext(unitName = "FortDayDB")
    protected EntityManager entityManager;

    private Class<T> type;

    public LocationManagementRepository(Class<T> type)
    {
        this.type = type;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public T findById(K id)
    {
        return entityManager.find(type, id);

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public T save(T entity)
    {
        return entityManager.merge(entity);

    }

}
