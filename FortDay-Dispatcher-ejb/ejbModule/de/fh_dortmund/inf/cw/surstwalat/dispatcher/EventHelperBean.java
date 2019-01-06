package de.fh_dortmund.inf.cw.surstwalat.dispatcher;

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

/**
 * Session Bean implementation class EventHelperBean
 * @author Johannes Heiderich
 */
@Stateless
public class EventHelperBean implements EventHelperLocal {
	
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	
	
	@Override
	public void triggerAssignPlayerEvent(Integer gameId, Integer userId, Integer playerNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.AssignPlayer);
		trySetIntProperty(message, PropertyType.UserId, userId);
		trySetObject(message, playerNo);
		sendMessage(message);
	}
	@Override
	public void triggerStartRoundEvent(Integer gameId, Integer roundNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.StartRound);
		trySetStringProperty(message, PropertyType.DisplayMessage, "Runde " + roundNo);
		trySetObject(message, roundNo);
		sendMessage(message);
	}
	@Override
	public void triggerAssignActivePlayerEvent(Integer gameId, Integer playerNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.AssignActivePlayer);
		trySetStringProperty(message, PropertyType.DisplayMessage, "Spieler " + playerNo + " ist an der Reihe");
		trySetObject(message, playerNo);
		sendMessage(message);
	}
	@Override
	public void triggerPlayerRollEvent(Integer gameId, Integer playerNo, Integer value) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.PlayerRoll);
		trySetIntProperty(message, PropertyType.PlayerNo, value);
		trySetStringProperty(message, PropertyType.DisplayMessage, "Spieler " + playerNo + " würfelt eine " + value);
		trySetObject(message, value);
		sendMessage(message);
	}
	@Override
	public void triggerEndRoundEvent(Integer gameId, Integer roundNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.EndRound);
		trySetObject(message, roundNo);
		sendMessage(message);
	}
	@Override
	public void triggerEliminatePlayerEvent(Integer gameId, Integer playerNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.EliminatePlayer);
		trySetStringProperty(message, PropertyType.DisplayMessage, "Spieler " + playerNo + " scheidet aus");
		trySetObject(message, playerNo);
		sendMessage(message);
	}
	
	private ObjectMessage createObjectMessage(Integer gameId, MessageType messageType) {
		ObjectMessage message = jmsContext.createObjectMessage();
		trySetIntProperty(message, PropertyType.MessageType, messageType.ordinal());
		trySetIntProperty(message, PropertyType.GameId, gameId);
		return message;
	}
	
	private void trySetIntProperty(Message message, PropertyType propertyType, Integer value) {
		try {
			message.setIntProperty(propertyType.toString(), value);
		} catch (JMSException e) {
			System.out.println("Failed to set" + propertyType.toString() + "to " + value);
		}
	}
	
	private void trySetStringProperty(Message message, PropertyType propertyType, String value) {
		try {
			message.setStringProperty(propertyType.toString(), value);
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
