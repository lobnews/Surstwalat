package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.events;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.EventHelperLocal;

/**
 * Session Bean implementation class EventHelperBean
 * @author Johannes Heiderich
 */
@Stateless
public class OutgoingEventHelperBean implements EventHelperLocal {
	
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	
	
	@Override
	public void triggerAssignPlayerEvent(Integer gameId, Integer userId, Integer playerNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.ASSIGN_PLAYER);
		trySetIntProperty(message, PropertyType.USER_ID, userId);
		trySetObject(message, playerNo);
		sendMessage(message);
	}
	@Override
	public void triggerStartRoundEvent(Integer gameId, Integer roundNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.START_ROUND);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Runde " + roundNo);
		trySetObject(message, roundNo);
		sendMessage(message);
	}
	@Override
	public void triggerAssignActivePlayerEvent(Integer gameId, Integer playerNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.ASSIGN_ACTIVE_PLAYER);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Spieler " + playerNo + " ist an der Reihe");
		trySetObject(message, playerNo);
		sendMessage(message);
	}
	@Override
	public void triggerPlayerRollEvent(Integer gameId, Integer playerNo, Integer value) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.PLAYER_ROLL);
		trySetIntProperty(message, PropertyType.PLAYER_NO, value);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Spieler " + playerNo + " würfelt eine " + value);
		trySetObject(message, value);
		sendMessage(message);
	}
	@Override
	public void triggerEndRoundEvent(Integer gameId, Integer roundNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.END_ROUND);
		trySetObject(message, roundNo);
		sendMessage(message);
	}
	@Override
	public void triggerEliminatePlayerEvent(Integer gameId, Integer playerNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.ELIMINATE_PLAYER);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Spieler " + playerNo + " scheidet aus");
		trySetObject(message, playerNo);
		sendMessage(message);
	}
	
	private ObjectMessage createObjectMessage(Integer gameId, int messageType) {
		ObjectMessage message = jmsContext.createObjectMessage();
		trySetIntProperty(message, PropertyType.MESSAGE_TYPE, messageType);
		trySetIntProperty(message, PropertyType.GAME_ID, gameId);
		return message;
	}
	
	private void trySetIntProperty(Message message, String propertyType, Integer value) {
		try {
			message.setIntProperty(propertyType, value);
		} catch (JMSException e) {
			System.out.println("Failed to set" + propertyType.toString() + "to " + value);
		}
	}
	
	private void trySetStringProperty(Message message, String propertyType, String value) {
		try {
			message.setStringProperty(propertyType, value);
		} catch (JMSException e) {
			System.out.println("Failed to set" + propertyType.toString() + "to " + value);
		}
	}
	
	private void trySetObject(ObjectMessage message, Serializable object) {
		try {
			message.setObject(object);
		} catch (JMSException e) {
			System.out.println("Failed to set object to" + object);
		}
	}
	
	private void sendMessage(ObjectMessage message) {
		jmsContext.createProducer().send(eventTopic, message);
	}

}
