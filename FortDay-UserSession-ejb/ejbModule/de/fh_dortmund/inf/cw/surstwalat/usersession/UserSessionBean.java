package de.fh_dortmund.inf.cw.surstwalat.usersession;

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
	
	@Override
	public void login() 
	{
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_LOGIN);
//		trySetIntProperty(msg, PropertyType.MESSAGE_TYPE, messageType);
//		trySetIntProperty(msg, PropertyType.GAME_ID, gameId);
		sendMessage(msg);
	}
	
	@Override
	public void logout() 
	{
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_LOGOUT);
//		trySetIntProperty(msg, PropertyType.MESSAGE_TYPE, messageType);
//		trySetIntProperty(msg, PropertyType.GAME_ID, gameId);
		sendMessage(msg);
	}
	
	@Override
	public void register() 
	{
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_REGISTER);
//		trySetIntProperty(msg, PropertyType.MESSAGE_TYPE, messageType);
//		trySetIntProperty(msg, PropertyType.GAME_ID, gameId);
		sendMessage(msg);
	}
	
	@Override
	public void disconnect() 
	{
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_DISCONNECT);
//		trySetIntProperty(msg, PropertyType.MESSAGE_TYPE, messageType);
//		trySetIntProperty(msg, PropertyType.GAME_ID, gameId);
		sendMessage(msg);
	}
	
	@Override
	public void timeout() 
	{
		ObjectMessage msg = createObjectMessage(2, MessageType.USER_TIMEOUT);
//		trySetIntProperty(msg, PropertyType.MESSAGE_TYPE, messageType);
//		trySetIntProperty(msg, PropertyType.GAME_ID, gameId);
		sendMessage(msg);
	}
	
	
	
	// administrative methods below //
	
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
