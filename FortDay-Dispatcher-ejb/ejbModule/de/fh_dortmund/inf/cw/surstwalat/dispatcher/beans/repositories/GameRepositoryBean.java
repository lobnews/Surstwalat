package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.GameRepositoryLocal;

/**
 * Session Bean implementation class GameRepositoryBean
 * @author Johannes Heiderich
 */
@Stateless
public class GameRepositoryBean extends DispatcherRepository<Game, Integer> implements GameRepositoryLocal {

    /**
     * Default constructor. 
     */
    public GameRepositoryBean() {
        super(Game.class);
    }

}
