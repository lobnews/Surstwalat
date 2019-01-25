package de.fh_dortmund.cw.surstwalat.ejb.ki;

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
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }, mappedName = "java:global/jms/FortDayEventTopic")
public class KiMessageBean implements MessageListener {

	public void onMessage(Message message) {
		ObjectMessage o = (ObjectMessage) message;
		int messageType = -1;
		try {
			messageType = o.getIntProperty(PropertyType.MESSAGE_TYPE);
		} catch (JMSException e) {
			// nothing to do, message type is still -1, switch-case will catch nothing
			System.out.println("Unable to get Message_Type from message at Ki.");
			e.printStackTrace();
		}
		switch (messageType) {
		case MessageType.GAME_STARTED:
			createKi(message);
			break;
		case MessageType.ASSIGN_ACTIVE_PLAYER:
			makeTurn(message);
			break;
		case MessageType.PLAYER_INVENTAR:
			refreshInventory(message);
			break;
		}

	}

	private void refreshInventory(Message message) {
		try {
			int userid = message.getIntProperty(PropertyType.PLAYER_NO);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void makeTurn(Message message) {
		int userid;
		try {
			userid = message.getIntProperty(PropertyType.USER1_ID);
			kiBean.makeTurn();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createKi(Message message) {

	}

}