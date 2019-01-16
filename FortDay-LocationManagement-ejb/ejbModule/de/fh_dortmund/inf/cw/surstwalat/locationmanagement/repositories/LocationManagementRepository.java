package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class LocationManagementRepository<T, K>
{

    @PersistenceContext(unitName = "SurstwalatDB")
    protected EntityManager entityManager;

    private Class<T> type;

    public LocationManagementRepository(Class<T> type)
    {
        this.type = type;
    }

    public T findById(K id)
    {
        try
        {
            return type.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            return null;
        }
    }

    public T save(T entity)
    {
        try
        {
            return type.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            return null;
        }

    }

}
