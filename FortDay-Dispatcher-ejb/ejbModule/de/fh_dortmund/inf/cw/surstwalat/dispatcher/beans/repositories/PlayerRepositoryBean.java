package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.PlayerRepositoryLocal;

/**
 * Session Bean implementation class PlayerRepositoryBean
 * @author Johannes Heiderich
 */
@Stateless
public class PlayerRepositoryBean extends DispatcherRepository<Player, Integer> implements PlayerRepositoryLocal {

    /**
     * Default constructor. 
     */
    public PlayerRepositoryBean() {
       super(Player.class);
    }
       
}
