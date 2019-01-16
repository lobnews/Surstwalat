package de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;

/**
 * @author Daniel Buschmann
 *
 */
public interface UserSession{

	public void login(String username, String password) throws Exception;
	
	public void logout();
	
	public void changePassword(String newPassword);
	
	public void register(String username, String password, String email);
	
	public void disconnect();
	
	public void timeout();
	
	public void updateEmailAddress(String email);
	
	public void deleteAccount();
	
	public void playerRolls(int gameID, int playerID, int value);
	
	public void startRound(int gameID, int number);
	
	public void userJoinedGame(int gameID);
	
	public void userCreatedGame();
	
	public void endRound(int gameID,int number);
	
	public void addItemToPlayer(int gameID, int playerID, Item item);
}
