package de.fh_dortmund.inf.cw.surstwalat.usersession.beans;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Action;
import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.UserManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.WrongPasswordException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.LoginFailedException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.util.HashManager;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSessionLocal;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSessionRemote;
import javax.ejb.EJB;

/**
 * @author Daniel Buschmann
 *
 */
@Stateful
public class UserSessionBean implements UserSessionLocal, UserSessionRemote {

    @Inject
    private JMSContext jmsContext;

    @EJB
    private UserManagementLocal userManagement;

    @Resource(lookup = "java:global/jms/FortDayEventTopic")
    private Topic eventTopic;

    private Account user;

    /**
     * Login account
     *
     * @param username account name
     * @param password account password
     * @throws AccountNotFoundException if account not exist
     * @throws LoginFailedException if input datas not match the account
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void login(String username, String password)
            throws AccountNotFoundException, LoginFailedException, GeneralServiceException {
        Account localAccount = new Account();
        localAccount.setName(username);
        password = HashManager.hashPassword(password);
        localAccount.setPassword(password);
        user = userManagement.login(localAccount);
    }

    /**
     * Update account password
     *
     * @param curPassword current password
     * @param newPassword new password
     * @throws WrongPasswordException if current password is wrong
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void changePassword(String curPassword, String newPassword)
            throws WrongPasswordException, GeneralServiceException {
        curPassword = HashManager.hashPassword(curPassword);
        newPassword = HashManager.hashPassword(newPassword);

        if (user.getPassword().contentEquals(curPassword)) {
            user.setPassword(newPassword);
            userManagement.changePassword(user);
        } else {
            throw new WrongPasswordException();
        }
    }

    /**
     * Update account password
     *
     * @param username account name
     * @param password password
     * @param email email address
     * @throws AccountAlreadyExistException if the account already exists.
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void register(String username, String password, String email)
            throws AccountAlreadyExistException, GeneralServiceException {
        Account localAccount = new Account();
        localAccount.setName(username);
        password = HashManager.hashPassword(password);
        localAccount.setPassword(password);
        localAccount.setEmail(email);
        userManagement.register(localAccount);
    }

    /**
     * Update account email address
     *
     * @param email new email address
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void updateEmailAddress(String email) throws GeneralServiceException {
        user.setEmail(email);
        userManagement.updateEmailAddress(user);
    }

    /**
     * Remove account
     *
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void deleteAccount() throws GeneralServiceException {
        userManagement.deleteAccount(user);
    }

    /**
     * Get user email
     *
     * @return email address
     */
    @Override
    public String getEMailAddress() {
        return user.getEmail();
    }

    /**
     * Get account name
     *
     * @return account name
     */
    @Override
    public String getAccountName() {
        return user.getName();
    }


    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#logout()
     */
    @Override
    public void logout() {
        ObjectMessage msg = createObjectMessage(2, MessageType.USER_LOGOUT);
        trySetObject(msg, user);
        sendMessage(msg);
		System.out.println("[USERSESSION] User logged out: Username: " + user.getName());
    }

    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#disconnect()
     */
    @Override
    public void disconnect() {
        ObjectMessage msg = createObjectMessage(-1, MessageType.USER_DISCONNECT);
        trySetObject(msg, user);
        sendMessage(msg);
		System.out.println("[USERSESSION] User disconnected: Username: " + user.getName());
    }

    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#timeout()
     */
    @Override
    public void timeout() {
        ObjectMessage msg = createObjectMessage(-1, MessageType.USER_TIMEOUT);
        trySetObject(msg, user);
        sendMessage(msg);
		System.out.println("[USERSESSION] User timed out: Username: " + user.getName());
    }

    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#playerRolls(int, int, de.fh_dortmund.inf.cw.surstwalat.common.model.Dice)
     */
    @Override
    public void playerRolls(int gameID, int playerID, Dice dice) {
        ObjectMessage msg = createObjectMessage(gameID, MessageType.PLAYER_ACTION);
        Action a = new Action();
        a.setActionType(ActionType.ROLL);
        trySetIntProperty(msg, PropertyType.ACTION_TYPE, a.getActionType().ordinal());
        trySetIntProperty(msg, PropertyType.PLAYER_NO, playerID);
        trySetObject(msg, dice);

        sendMessage(msg);
        
		System.out.println("[USERSESSION] Player rolls: GameID: " + gameID + ", Username: " + user.getName() + ", PlayerID: " + playerID);
    }

    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#useItem(int, int, de.fh_dortmund.inf.cw.surstwalat.common.model.Item)
     */
    @Override
    public void useItem(int gameID, int playerID, Item item) {
        ObjectMessage msg = createObjectMessage(gameID, MessageType.PLAYER_ACTION);
        Action a = new Action();
        a.setActionType(ActionType.USE_ITEM);
        trySetIntProperty(msg, PropertyType.ACTION_TYPE, a.getActionType().ordinal());
        trySetIntProperty(msg, PropertyType.PLAYER_NO, playerID);
        trySetObject(msg, item);

        sendMessage(msg);
		System.out.println("[USERSESSION] Player uses Item: GameID: " + gameID + ", Username: " + user.getName() + ", PlayerID: " + playerID);
    }

    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#startRound(int, int)
     */
    @Override
    public void startRound(int gameID, int number) {
        ObjectMessage msg = createObjectMessage(gameID, MessageType.START_ROUND);
        trySetIntProperty(msg, PropertyType.ROUND_NO, number);
        sendMessage(msg);
        
		System.out.println("[USERSESSION] Round started: GameID: " + gameID + ", Round: " + number);
    }

    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#userJoinedGame(int)
     */
    @Override
    public void userJoinedGame(int gameID) {
        ObjectMessage msg = createObjectMessage(gameID, MessageType.USER_JOINGAME);
        trySetObject(msg, user);

        sendMessage(msg);
        
		System.out.println("[USERSESSION] User joined game: GameID: " + gameID + ", Username: " + user.getName());
    }

    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#userCreatedGame()
     */
    @Override
    public void userCreatedGame() {
        ObjectMessage msg = createObjectMessage(0, MessageType.USER_CREATEGAME);
        trySetObject(msg, user);

        sendMessage(msg);
        
		System.out.println("[USERSESSION] User created game: Username: " + user.getName());
    }

    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#endRound(int, int)
     */
    @Override
    public void endRound(int gameID, int number) {
        ObjectMessage msg = createObjectMessage(gameID, MessageType.END_ROUND);
        trySetIntProperty(msg, PropertyType.ROUND_NO, number);

        sendMessage(msg);
        
		System.out.println("[USERSESSION] Round ends: GameID: " + gameID + ", Round: " + number);
    }

    /* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#addItemToPlayer(int, int, de.fh_dortmund.inf.cw.surstwalat.common.model.Item)
     */
    @Override
    public void addItemToPlayer(int gameID, int playerID, Item item) {
        ObjectMessage msg = createObjectMessage(gameID, MessageType.ADD_ITEM_TO_PLAYER);
        trySetIntProperty(msg, PropertyType.PLAYER_NO, playerID);
        trySetObject(msg, item);

        sendMessage(msg);
        
		System.out.println("[USERSESSION] Add item to Player: GameID: " + gameID + ", Username: " + user.getName() + ", PlayerID: " + playerID);
    }
    
    /* (non-Javadoc)
 	 * @see de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession#moveToken(int, int, int)
      */
    @Override
    public void moveToken(int gameID, int tokenID, int number) {
    	ObjectMessage msg = createObjectMessage(gameID, MessageType.MOVE_TOKEN);
    	trySetIntProperty(msg, PropertyType.TOKEN_ID, tokenID);
    	trySetObject(msg, number);
    	
    	sendMessage(msg);

    	System.out.println("[USERSESSION] Move Token: GameID: " + gameID + ", tokenID: " + tokenID + ", number: " + number);
    }

    
    
    // General methods for generating and sending messages below //
    /* Creates an Object message with the gameId and message Type */
    /**
     * @param gameId
     * @param messageType
     * @return
     */
    private ObjectMessage createObjectMessage(Integer gameId, int messageType) {
        ObjectMessage msg = jmsContext.createObjectMessage();
        trySetIntProperty(msg, PropertyType.MESSAGE_TYPE, messageType);
        trySetIntProperty(msg, PropertyType.GAME_ID, gameId);
        return msg;
    }

    /* Tries to set an Int Property; JMSException when failed */
    /**
     * @param msg
     * @param propertyType
     * @param value
     */
    private void trySetIntProperty(Message msg, String propertyType, Integer value) {
        try {
            msg.setIntProperty(propertyType, value);
        } catch (JMSException e) {
            System.out.println("Failed to set" + propertyType.toString() + "to " + value);
        }
    }

    /* Tries to set a String Property; JMSException when failed */
    /**
     * @param msg
     * @param propertyType
     * @param value
     */
    private void trySetStringProperty(Message msg, String propertyType, String value) {
        try {
            msg.setStringProperty(propertyType, value);
        } catch (JMSException e) {
            System.out.println("Failed to set" + propertyType.toString() + "to " + value);
        }
    }

    /* Tries to set an Object Property; JMSException when failed */
    /**
     * @param msg
     * @param object
     */
    private void trySetObject(ObjectMessage msg, Serializable object) {
        try {
            msg.setObject(object);
        } catch (JMSException e) {
            System.out.println("Failed to set object to" + object);
        }
    }

    /* Sends the given ObjectMessage */
    /**
     * @param msg
     */
    private void sendMessage(ObjectMessage msg) {
        jmsContext.createProducer().send(eventTopic, msg);
    }

}
