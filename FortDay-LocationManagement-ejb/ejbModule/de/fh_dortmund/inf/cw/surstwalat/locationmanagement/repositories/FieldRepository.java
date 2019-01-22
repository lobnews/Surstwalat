package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.repositories;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.model.PlayField;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.FieldRepositoryLocal;

@Stateless
public class FieldRepository extends LocationManagementRepository<PlayField, Integer> implements FieldRepositoryLocal
{

    public FieldRepository()
    {
        super(PlayField.class);
    }

    @Override
    public PlayField findFieldByItemId(int itemId)
    {
        TypedQuery<PlayField> query = entityManager.createNamedQuery("Field.getByItemId", PlayField.class);
        query.setParameter("itemId", itemId);
        return query.getSingleResult();
    }

}
