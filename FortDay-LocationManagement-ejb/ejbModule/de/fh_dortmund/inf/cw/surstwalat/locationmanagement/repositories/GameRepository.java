package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.repositories;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.interfaces.GameRepositoryLocal;

public class GameRepository extends LocationManagementRepository<Game, Integer> implements GameRepositoryLocal
{

    public GameRepository()
    {
        super(Game.class);
    }

}
