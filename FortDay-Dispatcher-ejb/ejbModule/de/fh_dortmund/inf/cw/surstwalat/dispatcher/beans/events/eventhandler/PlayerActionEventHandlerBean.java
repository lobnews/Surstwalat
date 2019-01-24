package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.events.eventhandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.interfaces.DispatcherLocal;

/**
 * Message-Driven Bean, which listens on PLAYER_ACTION messages on the FortDayEventTopic
 * @author Johannes Heiderich
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = PropertyType.MESSAGE_TYPE + " = "
				+ MessageType.PLAYER_ACTION) }, mappedName = "java:global/jms/FortDayEventTopic")
public class PlayerActionEventHandlerBean implements MessageListener {

	@EJB
	private DispatcherLocal dispatcher;


	/**
	 * @see MessageListener#onMessage(Message)
	 * Checks if the received message is of ACTION_TYPE ROLL and triggers the playerRoll Method at the DispatcherBean 
	 * @param message die empfangene Nachricht
	 */
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {

			ActionType type = ActionType.values()[message.getIntProperty(PropertyType.ACTION_TYPE)];
			if (type == ActionType.ROLL) {
				System.out.println("[DISPATCHER] PLAYER_ROLL received");
				ObjectMessage objectMessage = (ObjectMessage) message;
				Dice dice = objectMessage.getBody(Dice.class);
				dispatcher.playerRoll(message.getIntProperty(PropertyType.PLAYER_ID), dice);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
