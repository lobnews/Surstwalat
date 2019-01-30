package de.fh_dortmund.cw.surstwalat.ejb.itemmanagement;

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
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;

/**
 * @author Marvin Wölk
 *
 */
@Stateless
public class OutgoingEventHelperBean {

	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;

	public void sendAddItemToPlayground(Integer gameId, Item item, int pos) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.ITEM_SPAWN);
		trySetIntProperty(message, PropertyType.ITEM_ID, item.getId());
		trySetIntProperty(message, PropertyType.ITEM_POS, pos);
		sendMessage(message);
		System.out.println("[ITEMMANAG_" + gameId + "] Neues Item wurde erzeugt: " + item.getClass().getTypeName() + ":{" + item.getId() + ", pos:" + pos + "}");
	}

	public void sendInventar(Integer gameId, Player player) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.PLAYER_INVENTAR);
		trySetIntProperty(message, PropertyType.PLAYER_ID, player.getId());
		trySetObject(message, (Serializable) player.getItems());
		sendMessage(message);
	}
	
	public void sendAddItemToPlayer(Integer gameId, Integer playerId, Item item) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.ADD_ITEM_TO_PLAYER);
		trySetIntProperty(message, PropertyType.PLAYER_ID, playerId);
		trySetObject(message, item);
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
