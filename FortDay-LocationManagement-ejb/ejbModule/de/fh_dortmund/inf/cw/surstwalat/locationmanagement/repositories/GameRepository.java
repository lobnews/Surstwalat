package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.repositories;

import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.GameRepositoryLocal;

@Stateless
public class GameRepository extends LocationManagementRepository<Game, Integer> implements GameRepositoryLocal
{

    public GameRepository()
    {
        super(Game.class);
    }

}
