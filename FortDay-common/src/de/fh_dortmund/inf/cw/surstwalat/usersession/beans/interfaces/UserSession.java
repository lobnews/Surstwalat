package de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.WrongPasswordException;
import javax.persistence.NoResultException;
import javax.security.auth.login.FailedLoginException;

/**
 * @author Daniel Buschmann
 *
 */
public interface UserSession{

	public void login(String username, String password) 
                throws NoResultException, FailedLoginException, GeneralServiceException;
	
	public void logout();
	
	public void changePassword(String oldPassword, String newPassword) 
                throws WrongPasswordException, GeneralServiceException;
	
	public void register(String username, String password, String email) 
                throws AccountAlreadyExistException, GeneralServiceException;
	
	public void disconnect();
	
	public void timeout();
	
	public void updateEmailAddress(String email) throws GeneralServiceException;
	
	public void deleteAccount() throws GeneralServiceException;
	
	public void playerRolls(int gameID, int playerID, int value);
	
	public void startRound(int gameID, int number);
	
	public void userJoinedGame(int gameID);
	
	public void userCreatedGame();
	
	public void endRound(int gameID,int number);
	
	public void addItemToPlayer(int gameID, int playerID, Item item);
}
