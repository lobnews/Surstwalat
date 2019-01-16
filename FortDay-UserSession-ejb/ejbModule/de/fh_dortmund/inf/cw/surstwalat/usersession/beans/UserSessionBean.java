package de.fh_dortmund.inf.cw.surstwalat.usersession.beans;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.exceptions.UserNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.LobbyManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSessionLocal;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSessionRemote;

/**
 * @author Daniel Buschmann
 *
 */
@Stateful
public class UserSessionBean implements UserSessionLocal, UserSessionRemote{

	@Inject
	private JMSContext jmsContext;
	

	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;

	private Account user;

	@Override
	public void login(String username, String password) throws UserNotFoundException 
	{
		try {
			if (username.equals(user.getName()) && password.equals(user.getPassword()))
			{
				ObjectMessage msg = createObjectMessage(2, MessageType.USER_REGISTER);
				trySetObject(msg, user);
				sendMessage(msg);
			}
			else {
				throw new UserNotFoundException();
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void logout() 
	{
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_LOGOUT);
		trySetObject(msg, user);
		sendMessage(msg);
	}
	
        @Override
	public void changePassword(String oldPassword, String newPassword) {
		if (user.getPassword().contentEquals(oldPassword)) {
                        ObjectMessage msg = createObjectMessage(2, MessageType.USER_CHANGE_PASSWORD);
                        user.setPassword(newPassword);
                        trySetObject(msg, user);
                        sendMessage(msg);
		} else {
			try {
				throw new Exception("Wrong password");
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void register(String username, String password, String email) 
	{
		user.setName(username);
		user.setPassword(password);
		user.setEmail(email);
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_REGISTER);
		trySetObject(msg, user);
		sendMessage(msg);
	}

	@Override
	public void disconnect() 
	{
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_DISCONNECT);
		trySetObject(msg, user);
		sendMessage(msg);
	}

	@Override
	public void timeout() 
	{
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_TIMEOUT);
		trySetObject(msg, user);
		sendMessage(msg);
	}
	
	@Override
	public void updateEmailAddress(String email) {
		user.setEmail(email);
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_UPDATE_EMAIL);
		trySetObject(msg, user);
		sendMessage(msg);
	}
	
	@Override
	public void deleteAccount() 
	{
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_DELETE);
		trySetObject(msg, user);
		sendMessage(msg);
	}
	
	@Override
	public void playerRolls(int gameID, int playerID,  int value) {
		ObjectMessage msg = createObjectMessage(gameID, MessageType.PLAYER_ROLL);
		trySetIntProperty(msg, PropertyType.PLAYER_NO, playerID);
		//trySetIntProperty(msg, PropertyType.???, value);

		sendMessage(msg);		
	}
	
	@Override
	public void startRound(int gameID, int number) {
		ObjectMessage msg = createObjectMessage(gameID, MessageType.START_ROUND);
		//trySetIntProperty(msg, PropertyType.???, value);

		sendMessage(msg);			
	}
	
	@Override
	public void userJoinedGame(int gameID) {
		ObjectMessage msg = createObjectMessage(gameID, MessageType.USER_JOINGAME);
		trySetObject(msg, user);

		sendMessage(msg);		
	}

	@Override
	public void userCreatedGame() {
		ObjectMessage msg = createObjectMessage(0, MessageType.USER_CREATEGAME);
		trySetObject(msg, user);

		sendMessage(msg);			
	}
	
	@Override
	public void endRound(int gameID, int number) {
		ObjectMessage msg = createObjectMessage(gameID, MessageType.END_ROUND);
		trySetObject(msg, user);

		sendMessage(msg);		
	}

	@Override
	public void addItemToPlayer(int gameID, int playerID, Item item) {
		ObjectMessage msg = createObjectMessage(gameID, MessageType.ADD_ITEM_TO_PLAYER);
		trySetIntProperty(msg, PropertyType.PLAYER_NO, playerID);
		trySetObject(msg, item);

		sendMessage(msg);
	}
	
	


	// General methods for generating and sending messages below //
	
	/* Creates an Object message with the gameId and message Type */
	private ObjectMessage createObjectMessage(Integer gameId, int messageType) {
		ObjectMessage msg = jmsContext.createObjectMessage();
		trySetIntProperty(msg, PropertyType.MESSAGE_TYPE, messageType);
		trySetIntProperty(msg, PropertyType.GAME_ID, gameId);
		return msg;
	}
	
	/* Tries to set an Int Property; JMSException when failed */
	private void trySetIntProperty(Message msg, String propertyType, Integer value) {
		try {
			msg.setIntProperty(propertyType, value);
		} catch (JMSException e) {
			System.out.println("Failed to set" + propertyType.toString() + "to " + value);
		}
	}

	/* Tries to set a String Property; JMSException when failed */
	private void trySetStringProperty(Message msg, String propertyType, String value) {
		try {
			msg.setStringProperty(propertyType, value);
		} catch (JMSException e) {
			System.out.println("Failed to set" + propertyType.toString() + "to " + value);
		}
	}
	
	/* Tries to set an Object Property; JMSException when failed */
	private void trySetObject(ObjectMessage msg, Serializable object) {
		try {
			msg.setObject(object);
		} catch (JMSException e) {
			System.out.println("Failed to set object to" + object);
		}
	}
	
	/* Sends the given ObjectMessage */
	private void sendMessage(ObjectMessage msg) {
		jmsContext.createProducer().send(eventTopic, msg);
	}
}
