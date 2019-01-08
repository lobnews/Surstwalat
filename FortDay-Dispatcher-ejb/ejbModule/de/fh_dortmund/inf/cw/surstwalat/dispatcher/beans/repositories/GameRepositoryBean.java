package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.Game;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.GameRepositoryLocal;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class GameRepositoryBean
 * @author Johannes Heiderich
 */
@Stateless
public class GameRepositoryBean extends DispatcherRepository<Game, Long> implements GameRepositoryLocal {

    /**
     * Default constructor. 
     */
    public GameRepositoryBean() {
        super(Game.class);
    }

}
