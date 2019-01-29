package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.events.eventhandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.interfaces.DispatcherLocal;

/**
 * Message-Driven Bean, which listens on PLAYER_DEATH messages on the FortDayEventTopic
 * @author Johannes Heiderich
 */
@MessageDriven(
		activationConfig = { 
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
			@ActivationConfigProperty(propertyName = "messageSelector", 
									  propertyValue = PropertyType.MESSAGE_TYPE + " = " + MessageType.PLAYER_DEATH)
		}, 
		mappedName = "java:global/jms/FortDayEventTopic")
public class PlayerDeathEventHandler implements MessageListener {

	@EJB
	private DispatcherLocal dispatcher;

	/**
	 * @see MessageListener#onMessage(Message)
	 * Extracts the PLAYER_ID MessageProperty of the received message and triggers the onPlayerDeath method at the DispatcherBean
	 * @param message die empfangene Nachricht
	 */
    public void onMessage(Message message) {
    	try {
			System.out.println("[DISPATCHER] PLAYER_DEATH received");
			dispatcher.onPlayerDeath(message.getIntProperty(PropertyType.PLAYER_ID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
