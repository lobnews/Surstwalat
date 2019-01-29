package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;
/**
 * 
 * @author Johannes Heiderich
 *
 */
@Local
public interface EventHelperLocal {
	/**
	 * Sends a message of type ASSIGN_PLAYER
	 * @param gameId the id of the game
	 * @param userId the id of the user
	 * @param playerNo the number of the player
	 */
	void triggerAssignPlayerEvent(Integer gameId, Integer userId, Integer playerNo);
	/**
	 * Sends a message of type START_ROUND
	 * @param gameId the id of the game
	 * @param roundNo the current round of the game
	 */
	void triggerStartRoundEvent(Integer gameId, Integer roundNo);
	/**
	 * Sends a message of type ASSIGN_ACTIVE_PLAYER
	 * @param gameId the id of the game
	 * @param playerId the id of the player
	 * @param playerNo the number of the player
	 */
	void triggerAssignActivePlayerEvent(Integer gameId, Integer playerId, Integer playerNo, Integer timeout);
	/**
	 * Sends a message of type PLAYER_ROLL
	 * @param gameId the id of the game
	 * @param playerNo the number of the player
	 * @param value the roll value
	 */
	void triggerPlayerRollEvent(Integer gameId, Integer playerNo, Integer value);
	/**
	 * Sends a message of type ELIMINATE_PLAYER
	 * @param gameId the id of the game
	 * @param playerId the id of the player
	 * @param playerNo the number of the player
	 */
	void triggerEliminatePlayerEvent(Integer gameId, Integer playerId, Integer playerNo);
	/**
	 * Sends a message of type PLAYER_WINS
	 * @param gameId the id of the game
	 * @param playerId the id of the player
	 * @param playerNo the number of the player
	 */
	void triggerPlayerWinsEvent(Integer gameId, Integer playerId, Integer playerNo);
	
	/**
	 * Sends a message of type PLAYER_TIMEOUT_REMINDER
	 * @param gameId the id of the game
	 * @param playerId the id of the player
	 * @param playerNo the number of the player
	 */
	void triggerPlayerTimeoutReminderEvent(Integer gameId, Integer playerId, Integer playerNo, Integer secondsLeft);
}
