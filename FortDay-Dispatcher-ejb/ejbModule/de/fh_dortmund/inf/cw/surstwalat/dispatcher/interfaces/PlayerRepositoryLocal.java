package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
/**
 * 
 * @author Johannes Heiderich
 *
 */
@Local
public interface PlayerRepositoryLocal extends IDispatcherRepository<Player, Integer> {

}
