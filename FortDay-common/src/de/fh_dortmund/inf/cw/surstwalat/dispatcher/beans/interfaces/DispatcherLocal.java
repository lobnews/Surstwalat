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
	/**
	 * Creates a new player and assigns it to a user
	 * @param gameId the game id
	 */
	void createPlayers(int gameId);
	/**
	 * Selects a random number entry of a given dice object
	 * @param playerId the playerId
	 * @param dice the dice object
	 */
	void playerRoll(int playerId, Dice dice);
	/**
	 * Determines the next game round / next active player
	 * @param gameId the game id
	 */
	void dispatch(int gameId);
	
	/**
	 * Handles a player death
	 * @param playerId the player id
	 */
	void onPlayerDeath(int playerId);
}
