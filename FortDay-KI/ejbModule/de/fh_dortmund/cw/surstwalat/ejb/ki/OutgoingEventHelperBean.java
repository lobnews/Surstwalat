package de.fh_dortmund.cw.surstwalat.ejb.ki;

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
import de.fh_dortmund.inf.cw.surstwalat.common.model.Action;
import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;

@Stateless
public class OutgoingEventHelperBean {

	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;

	public void sendInventoryRequest(Integer gameId, Integer player_no) {
		ObjectMessage message = createObjectMessage(gameId, MessageType.SEND_PLAYER_INVENTAR);
		trySetIntProperty(message, PropertyType.PLAYER_NO, player_no);
		sendMessage(message);
	}
	
	public void sendUseItem(int gameid, int player_no, Item item) {
		ObjectMessage msg = createObjectMessage(gameid, MessageType.PLAYER_ACTION);
		Action a = new Action();
		a.setActionType(ActionType.USE_ITEM);
		trySetIntProperty(msg, PropertyType.ACTION_TYPE, a.getActionType().ordinal());
		trySetIntProperty(msg, PropertyType.PLAYER_NO, player_no);
		trySetObject(msg, item);
		sendMessage(msg);
	}
	
	public void sendPlayerRoll(int gameid, int player_no) {
		ObjectMessage msg = createObjectMessage(gameid, MessageType.PLAYER_ACTION);
		Action a = new Action();
		a.setActionType(ActionType.ROLL);
		trySetIntProperty(msg, PropertyType.ACTION_TYPE, a.getActionType().ordinal());
		trySetIntProperty(msg, PropertyType.PLAYER_NO, player_no);
		sendMessage(msg);
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