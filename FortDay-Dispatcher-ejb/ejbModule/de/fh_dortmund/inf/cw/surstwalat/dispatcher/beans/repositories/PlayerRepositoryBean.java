package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.repositories;

import de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain.Player;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.PlayerRepositoryLocal;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class PlayerRepositoryBean
 * @author Johannes Heiderich
 */
@Stateless
public class PlayerRepositoryBean extends DispatcherRepository<Player, Long> implements PlayerRepositoryLocal {

    /**
     * Default constructor. 
     */
    public PlayerRepositoryBean() {
       super(Player.class);
    }
       
}
