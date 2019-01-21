package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.repositories;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Field;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.FieldRepositoryLocal;

@Stateless
public class FieldRepository extends LocationManagementRepository<Field, Integer> implements FieldRepositoryLocal
{

    public FieldRepository()
    {
        super(Field.class);
    }

    @Override
    public Field findFieldByItemId(int itemId)
    {
        TypedQuery<Field> query = entityManager.createNamedQuery("Field.getByItemId", Field.class);
        query.setParameter("itemId", itemId);
        return query.getSingleResult();
    }

}
