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
 * Bean that provides functionality for sending messages to the FortDayEventTopic
 * @author Johannes Heiderich
 */
@Stateless
public class OutgoingEventHelperBean implements EventHelperLocal {
	
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;
	
	/**
	 * @see EventHelperLocal#triggerAssignPlayerEvent(Integer, Integer, Integer)
	 */
	@Override
	public void triggerAssignPlayerEvent(Integer gameId, Integer userId, Integer playerNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.ASSIGN_PLAYER);
		trySetIntProperty(message, PropertyType.USER_ID, userId);
		trySetObject(message, playerNo);
		sendMessage(message);
		System.out.println("[DISPATCHER] Assign Player " + playerNo + " to user with id " + userId);
	}
	
	/**
	 * @see EventHelperLocal#triggerStartRoundEvent(Integer, Integer)
	 */
	@Override
	public void triggerStartRoundEvent(Integer gameId, Integer roundNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.START_ROUND);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Runde " + roundNo);
		trySetObject(message, roundNo);
		sendMessage(message);
		System.out.println("[DISPATCHER] Started round " + roundNo);
	}
	
	/**
	 * @see EventHelperLocal#triggerAssignActivePlayerEvent(Integer, Integer, Integer)
	 */
	@Override
	public void triggerAssignActivePlayerEvent(Integer gameId, Integer playerId, Integer playerNo, Integer timeout) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.ASSIGN_ACTIVE_PLAYER);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Spieler " + playerNo + " ist an der Reihe");
		trySetIntProperty(message, PropertyType.PLAYER_ID, playerId);
		trySetIntProperty(message, PropertyType.TIMEOUT_SECONDS_LEFT, timeout);
		trySetObject(message, playerNo);
		sendMessage(message);
		System.out.println("[DISPATCHER] Player " + playerNo + "'s turn");
	}
	
	/**
	 * @see EventHelperLocal#triggerPlayerRollEvent(Integer, Integer, Integer)
	 */
	@Override
	public void triggerPlayerRollEvent(Integer gameId, Integer playerNo, Integer value) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.PLAYER_ROLL);
		trySetIntProperty(message, PropertyType.PLAYER_NO, value);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Spieler " + playerNo + " wuerfelt eine " + value);
		trySetObject(message, value);
		sendMessage(message);
		System.out.println("[DISPATCHER] Player " + playerNo + " rolls a " + value );
	}
	
	/**
	 * @see EventHelperLocal#triggerPlayerWinsEvent(Integer, Integer, Integer)
	 */
	@Override
	public void triggerPlayerWinsEvent(Integer gameId, Integer playerId, Integer playerNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.PLAYER_WINS);
		trySetIntProperty(message, PropertyType.PLAYER_ID, playerId);
		trySetIntProperty(message, PropertyType.PLAYER_NO, playerNo);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Spieler " + playerNo + " gewinnt");
		sendMessage(message);
		System.out.println("[DISPATCHER] Player " + playerNo + " wins" );
	}

	/**
	 * @see EventHelperLocal#triggerEliminatePlayerEvent(Integer, Integer, Integer)
	 */
	@Override
	public void triggerEliminatePlayerEvent(Integer gameId, Integer playerId, Integer playerNo) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.ELIMINATE_PLAYER);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Spieler " + playerNo + " scheidet aus");
		trySetIntProperty(message, PropertyType.PLAYER_ID, playerId);
		trySetObject(message, playerNo);
		sendMessage(message);
		System.out.println("[DISPATCHER] Player " + playerNo + " eliminated" );
	}
	
	
	/**
	 * @see EventHelperLocal#triggerEliminatePlayerEvent(Integer, Integer, Integer)
	 */
	@Override
	public void triggerPlayerTimeoutReminderEvent(Integer gameId, Integer playerId, Integer playerNo, Integer secondsLeft) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.ELIMINATE_PLAYER);
		trySetStringProperty(message, PropertyType.DISPLAY_MESSAGE, "Zug endet in " + secondsLeft + " Sekunden");
		trySetIntProperty(message, PropertyType.PLAYER_ID, playerId);
		trySetIntProperty(message, PropertyType.PLAYER_NO, playerNo);
		trySetObject(message, secondsLeft);
		sendMessage(message);
		System.out.println("[DISPATCHER] Timeout reminder for player " + playerNo + " (" + secondsLeft +" seconds left)" );
	}

	/**
	 * Produces an ObjectMessage object and sets a GAME_ID MessageProperty and given MessageType
	 * @param gameId ID of the game
	 * @param messageType type of the message
	 * @return ObjectMessage object
	 */
	private ObjectMessage createObjectMessage(Integer gameId, int messageType) {
		ObjectMessage message = jmsContext.createObjectMessage();
		trySetIntProperty(message, PropertyType.MESSAGE_TYPE, messageType);
		trySetIntProperty(message, PropertyType.GAME_ID, gameId);
		return message;
	}
	
	
	
	/**
	 * Wrapper method for setting MessageProperties of type Integer
	 * @param message message on which the property should be applied
	 * @param propertyType name of the property
	 * @param value Integer value of the property
	 */
	private void trySetIntProperty(Message message, String propertyType, Integer value) {
		try {
			message.setIntProperty(propertyType, value);
		} catch (JMSException e) {
			System.out.println("Failed to set" + propertyType.toString() + "to " + value);
		}
	}
	
	/**
	 * Wrapper method for setting MessageProperties of type Integer
	 * @param message message on which the property should be applied
	 * @param propertyType name of the property
	 * @param value String value of the property
	 */
	private void trySetStringProperty(Message message, String propertyType, String value) {
		try {
			message.setStringProperty(propertyType, value);
		} catch (JMSException e) {
			System.out.println("Failed to set" + propertyType.toString() + "to " + value);
		}
	}
	
	/**
	 * Wrapper method for setting a message body
	 * @param message message where the body should be applied
	 * @param object body object
	 */
	private void trySetObject(ObjectMessage message, Serializable object) {
		try {
			message.setObject(object);
		} catch (JMSException e) {
			System.out.println("Failed to set object to" + object);
		}
	}
	
	/**
	 * Sends a given message to the FortDayEventTopic
	 * @param message the message
	 */
	private void sendMessage(ObjectMessage message) {
		jmsContext.createProducer().send(eventTopic, message);
	}

}
