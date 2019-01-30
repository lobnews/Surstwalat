package de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
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
public interface UserSession {

    /**
     * @param username
     * @param password
     * @throws AccountNotFoundException
     * @throws LoginFailedException
     * @throws GeneralServiceException
     */
    public void login(String username, String password)
	    throws AccountNotFoundException, LoginFailedException, GeneralServiceException;

    /**
     *
     */
    public void logout();

    /**
     * @param oldPassword
     * @param newPassword
     * @throws WrongPasswordException
     * @throws GeneralServiceException
     */
    public void changePassword(String oldPassword, String newPassword)
	    throws WrongPasswordException, GeneralServiceException;

    /**
     * @param username
     * @param password
     * @param email
     * @throws AccountAlreadyExistException
     * @throws GeneralServiceException
     */
    public void register(String username, String password, String email)
	    throws AccountAlreadyExistException, GeneralServiceException;

    /**
     *
     */
    public void disconnect();

    /**
     *
     */
    public void timeout();

    /**
     * @param email
     * @throws GeneralServiceException
     */
    public void updateEmailAddress(String email) throws GeneralServiceException;

    /**
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
     * @param accountName
     * @return account
     * @throws AccountNotFoundException if account not exist
     * @throws GeneralServiceException  if there is a general service exception
     */
    public Account getAccountByName(String accountName)
	    throws AccountNotFoundException, GeneralServiceException;

    /**
     * @param accountId
     * @return account
     * @throws AccountNotFoundException if account not exist
     * @throws GeneralServiceException  if there is a general service exception
     */
    public Account getAccountById(int accountId)
	    throws AccountNotFoundException, GeneralServiceException;

    /**
     * @param gameID
     * @param playerID
     * @param dice
     */
    public void playerRolls(int gameID, int playerID, Dice dice);

    /**
     * @param gameID
     * @param number
     */
    public void startRound(int gameID, int number);

    /**
     * @param gameID
     */
    public void userJoinedGame(int gameID);

    /**
     *
     */
    public void userCreatedGame();

    /**
     * @param gameID
     * @param number
     */
    public void endRound(int gameID, int number);

    /**
     * @param gameID
     * @param playerID
     * @param item
     */
    public void addItemToPlayer(int gameID, int playerID, Item item);

    /**
     * @param gameID
     * @param playerID
     * @param item
     */
    public void useItem(int gameID, int playerID, Item item);
}
