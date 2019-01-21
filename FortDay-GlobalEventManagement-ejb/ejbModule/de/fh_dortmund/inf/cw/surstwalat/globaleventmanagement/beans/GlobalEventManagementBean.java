package de.fh_dortmund.inf.cw.surstwalat.globaleventmanagement.beans;


import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.globaleventmanagement.beans.interfaces.GlobalEventManagementLocal;

/**
 * 
 * @author Rebekka Michel
 */

@Stateless
public class GlobalEventManagementBean implements GlobalEventManagementLocal{

	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic eventTopic;

	@Override
	public void updateZone(int gameId, int currentZoneBegin, int currentZoneEnd, int nextZoneBegin, int nextZoneEnd, int damage) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.UPDATE_ZONE);
			message.setIntProperty(PropertyType.GAME_ID, gameId);
			message.setIntProperty(PropertyType.CURRENT_ZONE_BEGIN, currentZoneBegin);
			message.setIntProperty(PropertyType.CURRENT_ZONE_END, currentZoneEnd);
			message.setIntProperty(PropertyType.NEXT_ZONE_BEGIN, nextZoneBegin);
			message.setIntProperty(PropertyType.NEXT_ZONE_END, nextZoneEnd);
			message.setIntProperty(PropertyType.DAMAGE, damage);
			message.setStringProperty(PropertyType.DISPLAY_MESSAGE, "Die sichere Zone wurde aktualisiert!");
			jmsContext.createProducer().send(eventTopic, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void triggerAirdrop(int gameId) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TRIGGER_AIRDROP);
			message.setIntProperty(PropertyType.GAME_ID, gameId);
			jmsContext.createProducer().send(eventTopic, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void triggerStartingItems(int gameId) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TRIGGER_STARTING_ITEMS);
			message.setIntProperty(PropertyType.GAME_ID, gameId);
			jmsContext.createProducer().send(eventTopic, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void triggerDamage(int gameId, int playerNo, int characterId, int damage) {
		ObjectMessage message = jmsContext.createObjectMessage();
		try {
			message.setIntProperty(PropertyType.MESSAGE_TYPE, MessageType.TRIGGER_DAMAGE);
			message.setIntProperty(PropertyType.GAME_ID, gameId);
			message.setIntProperty(PropertyType.PLAYER_NO, playerNo);
			message.setIntProperty(PropertyType.CHARACTER_ID, characterId);
			message.setIntProperty(PropertyType.DAMAGE, damage);
			jmsContext.createProducer().send(eventTopic, message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
