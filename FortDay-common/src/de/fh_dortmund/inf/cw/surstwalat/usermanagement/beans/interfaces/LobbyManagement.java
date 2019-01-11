package de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.exceptions.GameIsFullException;

/**
 * 
 * @author Niklas Sprenger
 *
 */
public interface LobbyManagement {
	public void userLoggedIn(int userID);
	public void userDisconnected(int userID);
	public void userTimedOut(int userID);
	public void userCreatesGame(int userID);
	public void userJoinsGame(int userID, int gameID) throws GameIsFullException;
}
