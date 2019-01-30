package de.fh_dortmund.inf.cw.surstwalat.lobbymanagement.beans;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.lobbymanagement.beans.interfaces.LobbyManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;

/**
 * Cares about all messages that are coming in for the LobbyManagement. Parses them and passes
 * them to the LobbyManagementBean by calling the right methods.
 * @author Niklas Sprenger
 *
 */
@MessageDriven(
		activationConfig = { 
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		}, 
		mappedName = "java:global/jms/FortDayEventTopic")
public class LobbyManagementMessageReceiver implements MessageListener {

	@EJB
	private LobbyManagementLocal lobbyManagement;
	
	/**
	 * This method is called if there is an incoming message. It parses it to an object message and checks the message type.
	 * Depending on the message type, it calls a method to parse the parameters and forward them to the LobbyManagementBean.
	 * @param message The incoming message to be parsed
	 */
    public void onMessage(Message message) {
    	ObjectMessage o = (ObjectMessage) message;
    	int messageType = -1;
    	try {
			messageType = o.getIntProperty(PropertyType.MESSAGE_TYPE);
		} catch (JMSException e) {
			//nothing to do, message type is still -1, switch-case will catch nothing
			System.out.println("Unable to get Message_Type from message at LobbyManagement.");
			e.printStackTrace();
		}
    	switch(messageType) {
    	case MessageType.USER_CONNECTED: userConnected(message);break;
    	case MessageType.USER_DISCONNECTED:
    	case MessageType.USER_DISCONNECT: userDisconnected(message);break;
    	case MessageType.USER_TIMEOUT: userTimedOut(message);break;
    	case MessageType.USER_CREATEGAME: userCreatesGame(message);break;
    	case MessageType.USER_JOINGAME: userJoinsGame(message);break;
    	}
    	
    }
    
    /**
     * This method is called if a user is connected. Parse the incoming message and forward
     * its parameter to lobbyManagementBean.
     * @param message The incoming message to be parsed.
     */
    public void userConnected(Message message) {
    	try {
			System.out.println("LobbyMangement: Incoming Message of Type USER_CONNECTED");
        	int userid = message.getIntProperty(PropertyType.USER_ID);
    		lobbyManagement.userLoggedIn(userid);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * This method is called if a user is disconnects. Parse the incoming message and forward
     * its parameter to lobbyManagementBean.
     * @param message The incoming message to be parsed.
     */
    public void userDisconnected(Message message) {
    	try {
			System.out.println("LobbyMangement: Incoming Message of Type USER_DISCONNECTED or USER_DISCONNECT");
        	int userid = message.getIntProperty(PropertyType.USER_ID);
    		lobbyManagement.userDisconnected(userid);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * This method is called if a user timed out. Parse the incoming message and forward
     * its parameter to lobbyManagementBean.
     * @param message The incoming message to be parsed.
     */
    public void userTimedOut(Message message) {
    	try {
			System.out.println("LobbyMangement: Incoming Message of Type USER_TIMEOUT");
        	int userid = message.getIntProperty(PropertyType.USER_ID);
    		lobbyManagement.userTimedOut(userid);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * This method is called if a user created a game. Parse the incoming message and forward
     * its parameter to lobbyManagementBean.
     * @param message The incoming message to be parsed.
     */
    public void userCreatesGame(Message message) {
    	try {
			System.out.println("LobbyMangement: Incoming Message of Type USER_CREATEGAME");
        	int userid = message.getIntProperty(PropertyType.USER_ID);
    		lobbyManagement.userCreatesGame(userid);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * This method is called if a user tries to join a game. Parse the incoming message and forward
     * its parameter to lobbyManagementBean.
     * @param message The incoming message to be parsed.
     */
    public void userJoinsGame(Message message) {
    	try {
			System.out.println("LobbyMangement: Incoming Message of Type USER_JOINGAME");
        	int userid = message.getIntProperty(PropertyType.USER_ID);
        	int gameid = message.getIntProperty(PropertyType.GAME_ID);
    		lobbyManagement.userJoinsGame(userid,gameid);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
