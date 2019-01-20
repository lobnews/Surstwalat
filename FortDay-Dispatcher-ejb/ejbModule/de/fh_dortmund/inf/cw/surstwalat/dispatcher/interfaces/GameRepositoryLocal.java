package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Game;
/**
 * 
 * @author Johannes Heiderich
 *
 */
@Local
public interface GameRepositoryLocal extends IDispatcherRepository<Game, Integer> {

}
