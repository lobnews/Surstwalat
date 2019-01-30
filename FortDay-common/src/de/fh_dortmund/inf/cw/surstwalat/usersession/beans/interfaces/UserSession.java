package de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.LoginFailedException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.WrongPasswordException;

/**
 * @author Daniel Buschmann
 *
 */
public interface UserSession{

	/**
	 * Login for the User
	 * 
	 * @param username
	 * @param password
	 * @throws AccountNotFoundException
	 * @throws LoginFailedException
	 * @throws GeneralServiceException
	 */
	public void login(String username, String password) 
                throws AccountNotFoundException, LoginFailedException, GeneralServiceException;
	
	/**
	 * Logout for the User
	 */
	public void logout();
	
	/**
	 * Changes the password of the User
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @throws WrongPasswordException
	 * @throws GeneralServiceException
	 */
	public void changePassword(String oldPassword, String newPassword) 
                throws WrongPasswordException, GeneralServiceException;
	
	/**
	 * Register an User
	 * @param username
	 * @param password
	 * @param email
	 * @throws AccountAlreadyExistException
	 * @throws GeneralServiceException
	 */
	public void register(String username, String password, String email) 
                throws AccountAlreadyExistException, GeneralServiceException;
	
	/**
	 * Disconnect the User
	 */
	public void disconnect();
	
	/**
	 * Timeout for the User
	 */
	public void timeout();
	
	/**
	 * Update the Emailaddress of the User
	 * 
	 * @param email
	 * @throws GeneralServiceException
	 */
	public void updateEmailAddress(String email) throws GeneralServiceException;
	
	/**
	 * Delete the Account
	 * 
	 * @throws GeneralServiceException
	 */
	public void deleteAccount() throws GeneralServiceException;
	
    /**
     * @return email
     */
    public String getEMailAddress();
    
    /**
     * @return account name
     */
    public String getAccountName();
        
	/**
	 * Action when the Player rolls
	 * 
	 * @param gameID
	 * @param playerID
	 * @param dice
	 */
	public void playerRolls(int gameID, int playerID, Dice dice);
	
	/**
	 * Starts the given Round
	 * 
	 * @param gameID
	 * @param number
	 */
	public void startRound(int gameID, int number);
	
	/**
	 * User joined the given game
	 * 
	 * @param gameID
	 */
	public void userJoinedGame(int gameID);
	
	/**
	 * User created a game
	 */
	public void userCreatedGame();
	
	/**
	 * End the given round
	 * 
	 * @param gameID
	 * @param number
	 */
	public void endRound(int gameID,int number);
	
	/**
	 * Add the given Item to the Player
	 * 
	 * @param gameID
	 * @param playerID
	 * @param item
	 */
	public void addItemToPlayer(int gameID, int playerID, Item item);
	
	/**
	 * Use the given Item
	 * 
	 * @param gameID
	 * @param playerID
	 * @param item
	 */
	public void useItem(int gameID, int playerID, Item item);
	
	/**
	 * Moves a Token by the given number
	 * 
	 * @param gameID
	 * @param tokenID
	 * @param number
	 */
	public void moveToken(int gameID, int tokenID, int number);
}
