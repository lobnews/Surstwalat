package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.events.eventhandler;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces.LocationManagementLocal;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = PropertyType.MESSAGE_TYPE + " = "
				+ MessageType.TOKEN_CREATED) // TODO: richtiger Type
}, mappedName = "java:global/jms/FortDayEventTopic")
public class TokenCreatedEventHandlerBean implements MessageListener {

	@EJB
	LocationManagementLocal locationManagement;

	@Override
	public void onMessage(Message message) {
		System.out.println("[LOCATIONMANAGEMENT] TOKEN_CREATED received");
		try {
			List<Integer> tokenIds = ((ObjectMessage) message).getBody(LinkedList.class);
			locationManagement.addTokenToPlayground(message.getIntProperty(PropertyType.GAME_ID),
					message.getIntProperty(PropertyType.PLAYER_ID), tokenIds);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
