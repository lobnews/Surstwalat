package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.repositories;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.PlaygroundRepositoryLocal;

@Stateless
public class PlaygroundRepository extends LocationManagementRepository<Playground, Long> implements PlaygroundRepositoryLocal
{

    public PlaygroundRepository()
    {
        super(Playground.class);
    }

    @Override
    public Playground getByGameId(int gameID)
    {
        TypedQuery<Playground> query = entityManager.createNamedQuery("Playground.getByGameId",Playground.class);
        query.setParameter("gameId", gameID);
        
        return query.getSingleResult();
    }
    
    

}
