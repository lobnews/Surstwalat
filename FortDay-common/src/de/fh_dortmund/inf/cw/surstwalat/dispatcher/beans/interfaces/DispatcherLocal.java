package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;

/**
 * 
 * @author Johannes Heiderich
 *
 */
@Local
public interface DispatcherLocal extends Dispatcher {
	void createPlayers(int gameId);
	void playerRoll(int playerId, Dice dice);
	void dispatch(int gameId);
	void onPlayerDeath(int playerId);
}
