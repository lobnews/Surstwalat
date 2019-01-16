package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces;

public interface ILocationManagement<T,K>
{
    
    public T findById(K id);

    public T save(T entity);
}
